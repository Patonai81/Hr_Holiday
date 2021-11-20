package hu.webuni.hrholiday.szabi.service;

import hu.webuni.hrholiday.szabi.model.Employee;
import hu.webuni.hrholiday.szabi.model.HolidayRequest;
import hu.webuni.hrholiday.szabi.model.HolidayRequestStatus;
import hu.webuni.hrholiday.szabi.repository.EmployeeRepository;
import hu.webuni.hrholiday.szabi.repository.HolidayRequestRepository;
import hu.webuni.hrholiday.szabi.web.exception.EmployeeCannotBeFoundException;
import hu.webuni.hrholiday.szabi.web.exception.HolidayRequestCannotBeFoundException;
import hu.webuni.hrholiday.szabi.web.exception.HolidayRequestCannotBeUpdatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static hu.webuni.hrholiday.szabi.web.exception.CustomErrorCodes.*;

@Service
public class HolidayRequestService {

    @Autowired
    HolidayRequestRepository holidayRequestRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Transactional
    public HolidayRequest createHolidayRequest(HolidayRequest holidayRequest) {

        Employee employeeFromRepo = employeeRepository.findById(holidayRequest.getEmployee().getEmployeeId()).orElseThrow(() -> new EmployeeCannotBeFoundException(CUSTOMER_NOT_FOUND));
        employeeFromRepo.getHolidayRequestsList().add(holidayRequest);
        holidayRequest.setHolidayRequestStatus(HolidayRequestStatus.CREATED);
        holidayRequest.setCreationTimestamp(LocalDateTime.now());
        HolidayRequest holidayRequestFromRepo = holidayRequestRepository.save(holidayRequest);

        return holidayRequestFromRepo;
    }

    @Transactional
    public HolidayRequest updateRequest(HolidayRequest holidayRequest) {

        HolidayRequest holidayRequestFromRepo = holidayRequestRepository.findById(holidayRequest.getHolidayRequestId()).orElseThrow(() -> new HolidayRequestCannotBeFoundException(HOLIDAY_NOT_FOUND));
        HolidayRequestStatus currentStatus = holidayRequestFromRepo.getHolidayRequestStatus();
        //A főnök sem updatelheti 2X a státuszt
        if (currentStatus == HolidayRequestStatus.DENIED || currentStatus == HolidayRequestStatus.ACCEPTED)
            throw new HolidayRequestCannotBeUpdatedException(HOLIDAY_NOT_MODIFY, currentStatus);

        holidayRequestFromRepo = holidayRequestRepository.save(holidayRequest);

        return holidayRequestFromRepo;
    }


}
