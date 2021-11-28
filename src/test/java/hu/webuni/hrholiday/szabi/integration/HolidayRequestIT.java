package hu.webuni.hrholiday.szabi.integration;

import hu.webuni.hrholiday.szabi.model.Boss;
import hu.webuni.hrholiday.szabi.model.Employee;
import hu.webuni.hrholiday.szabi.model.HolidayRequest;
import hu.webuni.hrholiday.szabi.model.HolidayRequestStatus;
import hu.webuni.hrholiday.szabi.repository.HolidayRequestRepository;
import hu.webuni.hrholiday.szabi.service.EmployeeService;
import hu.webuni.hrholiday.szabi.service.HolidayRequestService;
import hu.webuni.hrholiday.szabi.service.InitDBService;
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

    @Autowired
    InitDBService initDBService;

    @BeforeEach
    public void initDb() {
      initDBService.initDb();
    }

    @Test
    public void testUpdateSuccess() {
        List<HolidayRequest> holidayRequestList = holidayRequestRepository.findHolidayRequestByStatus(HolidayRequestStatus.CREATED);
        HolidayRequest tesztRequest = holidayRequestList.get(0);
        tesztRequest.setHolidayRequestStatus(HolidayRequestStatus.ACCEPTED);
        HolidayRequest holidayRequestFromRepo = holidayRequestService.updateHolidayRequest(tesztRequest);
        assertThat(holidayRequestFromRepo.getHolidayRequestStatus()).isEqualTo(HolidayRequestStatus.ACCEPTED);
    }

    @Test
    public void testFailureSuccess() {
        List<HolidayRequest> holidayRequestList = holidayRequestRepository.findHolidayRequestByStatus(HolidayRequestStatus.DENIED);
        HolidayRequest tesztRequest = holidayRequestList.get(0);
        tesztRequest.setHolidayRequestStatus(HolidayRequestStatus.ACCEPTED);

        assertThatThrownBy(() -> {
            HolidayRequest holidayRequestFromRepo = holidayRequestService.updateHolidayRequest(tesztRequest);
        }).isInstanceOf(HolidayRequestCannotBeUpdatedException.class);

    }
}
