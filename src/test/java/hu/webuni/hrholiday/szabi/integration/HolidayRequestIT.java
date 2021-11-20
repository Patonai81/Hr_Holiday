package hu.webuni.hrholiday.szabi.integration;

import hu.webuni.hrholiday.szabi.model.Boss;
import hu.webuni.hrholiday.szabi.model.Employee;
import hu.webuni.hrholiday.szabi.model.HolidayRequest;
import hu.webuni.hrholiday.szabi.model.HolidayRequestStatus;
import hu.webuni.hrholiday.szabi.repository.HolidayRequestRepository;
import hu.webuni.hrholiday.szabi.service.EmployeeService;
import hu.webuni.hrholiday.szabi.service.HolidayRequestService;
import hu.webuni.hrholiday.szabi.web.exception.HolidayRequestCannotBeUpdatedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@AutoConfigureTestDatabase
@SpringBootTest(properties = {"spring.config.name=myapp-test-h2", "myapp.trx.datasource.url=jdbc:h2:mem:trxServiceStatus"})
//@SpringBootTest
public class HolidayRequestIT {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    HolidayRequestService holidayRequestService;

    @Autowired
    HolidayRequestRepository holidayRequestRepository;

    @BeforeEach
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
        holidayRequestService.updateRequest(holidayRequestFromRepo);


    }

    @Test
    public void testUpdateSuccess() {
        List<HolidayRequest> holidayRequestList = holidayRequestRepository.findHolidayRequestByStatus(HolidayRequestStatus.CREATED);
        HolidayRequest tesztRequest = holidayRequestList.get(0);
        tesztRequest.setHolidayRequestStatus(HolidayRequestStatus.ACCEPTED);
        HolidayRequest holidayRequestFromRepo = holidayRequestService.updateRequest(tesztRequest);
        assertThat(holidayRequestFromRepo.getHolidayRequestStatus()).isEqualTo(HolidayRequestStatus.ACCEPTED);
    }

    @Test
    public void testFailureSuccess() {
        List<HolidayRequest> holidayRequestList = holidayRequestRepository.findHolidayRequestByStatus(HolidayRequestStatus.DENIED);
        HolidayRequest tesztRequest = holidayRequestList.get(0);
        tesztRequest.setHolidayRequestStatus(HolidayRequestStatus.ACCEPTED);

        assertThatThrownBy(() -> {
            HolidayRequest holidayRequestFromRepo = holidayRequestService.updateRequest(tesztRequest);
        }).isInstanceOf(HolidayRequestCannotBeUpdatedException.class);

    }
}
