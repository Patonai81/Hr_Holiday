package hu.webuni.hrholiday.szabi.service;

import hu.webuni.hrholiday.szabi.model.Boss;
import hu.webuni.hrholiday.szabi.model.Employee;
import hu.webuni.hrholiday.szabi.model.HolidayRequest;
import hu.webuni.hrholiday.szabi.model.HolidayRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class InitDBService {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    HolidayRequestService holidayRequestService;

    public void initDb() {
        Boss emp1 = new Boss("Szabi Test Boss", "FloorManager");
        employeeService.createEmployee(emp1);
        Employee emp2 = new Employee("Réka Test");
        emp2.setBoss(emp1);
        employeeService.createEmployee(emp2);

        Employee emp3 = new Employee("Bori Test");
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
