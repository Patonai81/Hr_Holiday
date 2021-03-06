package hu.webuni.hrholiday.szabi.service;

import hu.webuni.hrholiday.szabi.model.Boss;
import hu.webuni.hrholiday.szabi.model.Employee;
import hu.webuni.hrholiday.szabi.model.HolidayRequest;
import hu.webuni.hrholiday.szabi.model.HolidayRequestStatus;
import hu.webuni.hrholiday.szabi.repository.EmployeeRepository;
import hu.webuni.hrholiday.szabi.repository.HolidayRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Component
public class InitDBService {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    HolidayRequestService holidayRequestService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    HolidayRequestRepository holidayRequestRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void initDb() {
        holidayRequestRepository.deleteAll();
        employeeRepository.deleteAll();


        Boss emp1 = new Boss("Szabi Test Boss","Szabi", passwordEncoder.encode("Szabi"), "FloorManager");
        employeeService.createEmployee(emp1);

        Employee emp2 = new Employee("Réka Test","Reka", passwordEncoder.encode("Reka"));
        emp2.setBoss(emp1);
        employeeService.createEmployee(emp2);

        Employee emp3 = new Employee("Bori Test","Bori", passwordEncoder.encode("Bori"));
        emp3.setBoss(emp1);
        employeeService.createEmployee(emp3);

        HolidayRequest holidayRequest = new HolidayRequest(emp2, LocalDate.of(2021, 9, 12), LocalDate.of(2021, 9, 15));
        holidayRequestService.createHolidayRequest(holidayRequest);

        holidayRequest = new HolidayRequest(emp2, LocalDate.of(2021, 10, 10), LocalDate.of(2021, 10, 20));
        holidayRequestService.createHolidayRequest(holidayRequest);

        holidayRequest = new HolidayRequest(emp2, LocalDate.of(2021, 12, 12), LocalDate.of(2021, 12, 20));
        holidayRequestService.createHolidayRequest(holidayRequest);

        holidayRequest = new HolidayRequest(emp3, LocalDate.of(2021, 6, 10), LocalDate.of(2021, 6, 20));
        holidayRequestService.createHolidayRequest(holidayRequest);

        holidayRequest = new HolidayRequest(emp3, LocalDate.of(2021, 4, 12), LocalDate.of(2021, 4, 20));
        holidayRequestService.createHolidayRequest(holidayRequest);

        holidayRequest = new HolidayRequest(emp3, LocalDate.of(2021, 10, 10), LocalDate.of(2021, 10, 20));
        HolidayRequest holidayRequestFromRepo = holidayRequestService.createHolidayRequest(holidayRequest);

        holidayRequestFromRepo.setHolidayRequestStatus(HolidayRequestStatus.DENIED);
        holidayRequestService.updateHolidayRequest(holidayRequestFromRepo);


    }

}
