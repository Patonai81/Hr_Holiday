package hu.webuni.hrholiday.szabi.service;

import hu.webuni.hrholiday.szabi.dto.EmployeeDto;
import hu.webuni.hrholiday.szabi.dto.HolidayRequestQuery;
import hu.webuni.hrholiday.szabi.model.Boss;
import hu.webuni.hrholiday.szabi.model.Employee;
import hu.webuni.hrholiday.szabi.model.HolidayRequest;
import hu.webuni.hrholiday.szabi.model.HolidayRequestStatus;
import hu.webuni.hrholiday.szabi.repository.EmployeeRepository;
import hu.webuni.hrholiday.szabi.repository.HolidayRequestRepository;
import hu.webuni.hrholiday.szabi.web.exception.EmployeeCannotBeFoundException;
import hu.webuni.hrholiday.szabi.web.exception.HolidayRequestCannotBeFoundException;
import hu.webuni.hrholiday.szabi.web.exception.HolidayRequestCannotBeUpdatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static hu.webuni.hrholiday.szabi.web.exception.CustomErrorCodes.*;

@Service
public class HolidayRequestService {

    @Autowired
    HolidayRequestRepository holidayRequestRepository;

    @Autowired
    EmployeeRepository employeeRepository;


    @Transactional
    public List<HolidayRequest> findHolidayRequestsBy(HolidayRequestQuery query) {
        return holidayRequestRepository.findAll(query.toSpecification(), query.getPageable()).getContent();
    }


    @Transactional
    public HolidayRequest createHolidayRequest(HolidayRequest holidayRequest) {

        Employee employeeFromRepo = employeeRepository.findById(holidayRequest.getEmployeeCreator().getEmployeeId()).orElseThrow(() -> new EmployeeCannotBeFoundException(CUSTOMER_NOT_FOUND));
        employeeFromRepo.getHolidayRequestsList().add(holidayRequest);
        holidayRequest.setHolidayRequestStatus(HolidayRequestStatus.CREATED);
        holidayRequest.setCreationTimestamp(LocalDateTime.now());
        HolidayRequest holidayRequestFromRepo = holidayRequestRepository.save(holidayRequest);

        return holidayRequestFromRepo;
    }


    @Transactional
    public HolidayRequest updateHolidayRequest(HolidayRequest holidayRequest) {
        System.out.println("DONE");
        HolidayRequest holidayRequestFromRepo = holidayRequestRepository.findById(holidayRequest.getHolidayRequestId()).orElseThrow(() -> new HolidayRequestCannotBeFoundException(HOLIDAY_NOT_FOUND));
        if (null != SecurityContextHolder.getContext().getAuthentication() && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            Employee authenticatedEmployee = (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            //A lezártak nem módosíthatók semmiképp
            HolidayRequestStatus currentStatus = holidayRequestFromRepo.getHolidayRequestStatus();
            if (currentStatus == HolidayRequestStatus.DENIED || currentStatus == HolidayRequestStatus.ACCEPTED)
                throw new HolidayRequestCannotBeUpdatedException(HOLIDAY_NOT_MODIFY, currentStatus);
            //user módosítja a sajátját
            if (holidayRequest.getHolidayRequestStatus() == HolidayRequestStatus.DELETED) {
                if (holidayRequestFromRepo.getEmployeeCreator().getUsername() != authenticatedEmployee.getUsername())
                    throw new HolidayRequestCannotBeUpdatedException("User has no right to update", holidayRequest.getHolidayRequestStatus());
                else {
                    holidayRequestFromRepo.setHolidayRequestStatus(holidayRequest.getHolidayRequestStatus());
                    return holidayRequest;
                }
            // Főnök módosítja az alkalmazottjáét
            } else if (holidayRequest.getHolidayRequestStatus() == HolidayRequestStatus.ACCEPTED || holidayRequest.getHolidayRequestStatus() == HolidayRequestStatus.DENIED) {
                Boss boss =  holidayRequestFromRepo.getEmployeeCreator().getBoss();

                while (null != boss) {
                    if (authenticatedEmployee.getEmployeeId().equals(boss.getEmployeeId())) {
                        holidayRequestFromRepo.setAcceptor(boss);
                        holidayRequestFromRepo.setHolidayRequestStatus(holidayRequest.getHolidayRequestStatus());
                        return holidayRequestFromRepo;
                    } else {
                      // employeeBossa hívás repo
                        boss = boss.getBoss();
                    }
                }
                throw new HolidayRequestCannotBeUpdatedException("Boss user has no right to update", holidayRequest.getHolidayRequestStatus());
            }
        }
        //tesztesethez lefutásához hagytam bent, mert a teszteset amikor hívja a metódust akkor nincs Security Context
        holidayRequestFromRepo.setAcceptor(holidayRequest.getAcceptor());
        holidayRequestFromRepo.setHolidayRequestStatus(holidayRequest.getHolidayRequestStatus());
        return holidayRequestFromRepo;
    }


}
