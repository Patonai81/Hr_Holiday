package hu.webuni.hrholiday.szabi.integration;

import hu.webuni.hrholiday.szabi.dto.BossDto;
import hu.webuni.hrholiday.szabi.dto.EmployeeDto;
import hu.webuni.hrholiday.szabi.dto.HolidayRequestDto;
import hu.webuni.hrholiday.szabi.dto.HolidayRequestQuery;
import hu.webuni.hrholiday.szabi.model.Employee;
import hu.webuni.hrholiday.szabi.model.HolidayRequestStatus;
import hu.webuni.hrholiday.szabi.repository.EmployeeRepository;
import hu.webuni.hrholiday.szabi.service.InitDBService;
import hu.webuni.hrholiday.szabi.web.exception.ErrorContainer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Profile("test")

public class HolidayRestControllerTest {

    @Autowired
    WebTestClient webClient;

    @Autowired
    InitDBService initDBService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmployeeRepository employeeRepository;

    private String userName="user";
    private String password="pass";

    @BeforeAll
    public void testInitDb() {
        initDBService.initDb();

           if (employeeRepository.findEmployeeByUsername(userName).isEmpty()){
               Employee employee = new Employee();
               employee.setEmployeeName("Teszt");
               employee.setUsername(userName);
               employee.setPassword(passwordEncoder.encode(password));
               employeeRepository.save(employee);
           }

    }


    @Test
    public void testCreateEmployeePositive() {

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEmployeeName("First Employee");
        employeeDto.setUsername("Test UserName");
        employeeDto.setPassword("Testpassword");

        webClient.post()
                .uri("/api/employee/createEmployee")
                .headers(httpHeaders ->  httpHeaders.setBasicAuth(userName,password))
                .body(Mono.just(employeeDto), EmployeeDto.class)
                .exchange()
                .expectStatus()
                .isOk();

    }


    @Test
    public void testCreateHolidayRequestPositive() {

        EmployeeDto employeeDto = getEmployees().get(0);


        HolidayRequestDto holidayRequest = new HolidayRequestDto(employeeDto, LocalDate.of(2021, 9, 12), LocalDate.of(2021, 9, 15));

        webClient.post()
                .uri("/api/holidayRequest")
                .headers(httpHeaders ->  httpHeaders.setBasicAuth(userName,password))
                .bodyValue(holidayRequest)
                .exchange()
                .expectBody(HolidayRequestDto.class)
                .value(item -> {
                    assertThat(item.getHolidayRequestStatus()).isEqualTo(HolidayRequestStatus.CREATED);
                });

    }

    //NullPointer váltódik ki a validálás során, amit nem tudok megfelelően kezelni
    @Test
    public void testCreateHolidayRequestNegative() {

        EmployeeDto employeeDto = getEmployees().get(0);
        HolidayRequestDto holidayRequest = new HolidayRequestDto(employeeDto, null, LocalDate.of(2021, 9, 15));


        webClient.post()
                .uri("/api/holidayRequest")
                .headers(httpHeaders ->  httpHeaders.setBasicAuth(userName,password))
                .bodyValue(holidayRequest)
                .exchange().expectStatus().isBadRequest().expectBody(ErrorContainer.class);

    }

    @Test
    public void testAcceptHolidayRequestPositive() {

        //Acceptor boss
        EmployeeDto employeeFromRepo = getEmployees().get(2);

        //Conditions
        HolidayRequestQuery query = new HolidayRequestQuery();
        query.setHolidayRequestStatus(HolidayRequestStatus.CREATED);

        List<HolidayRequestDto> holidayRequestList = getHolidays(query);

        HolidayRequestDto holidayRequestDto = holidayRequestList.get(0);
        holidayRequestDto.setAcceptor(new BossDto(employeeFromRepo));
        holidayRequestDto.setHolidayRequestStatus(HolidayRequestStatus.ACCEPTED);

        webClient.post()
                .uri("/api/holidayRequest/manageHolidayRequest")
                .headers(httpHeaders ->  httpHeaders.setBasicAuth("Szabi","Szabi"))
                .bodyValue(holidayRequestDto)
                .exchange()
                .expectBody(HolidayRequestDto.class)
                .value(item -> {
                    assertThat(item.getHolidayRequestStatus()).isEqualTo(HolidayRequestStatus.ACCEPTED);
                });


    }

    @Test
    public void testAcceptHolidayRequestNegative() {

        //Acceptor boss
        EmployeeDto employeeFromRepo = getEmployees().get(2);

        //Conditions
        HolidayRequestQuery query = new HolidayRequestQuery();
        query.setHolidayRequestStatus(HolidayRequestStatus.DENIED);

        List<HolidayRequestDto> holidayRequestList = getHolidays(query);

        HolidayRequestDto holidayRequestDto = holidayRequestList.get(0);
        holidayRequestDto.setAcceptor(new BossDto(employeeFromRepo));
        holidayRequestDto.setHolidayRequestStatus(HolidayRequestStatus.ACCEPTED);


        webClient.post()
                .uri("/api/holidayRequest/manageHolidayRequest")
                .headers(httpHeaders ->  httpHeaders.setBasicAuth("Szabi","Szabi"))
                .bodyValue(holidayRequestDto)
                .exchange()
                .expectBody(ErrorContainer.class);

    }

    @Test
    public void testFindHolidays_BYType() {

        //Conditions
        HolidayRequestQuery query = new HolidayRequestQuery();
        query.setHolidayRequestStatus(HolidayRequestStatus.DENIED);
        List<HolidayRequestDto> holidayRequestList = getHolidays(query);

        assertThat(holidayRequestList).hasSize(1);
    }

    @Test
    public void testFindHolidays_BYTYPE_AND_CREATION_TIME_FROM() {

        //Conditions
        HolidayRequestQuery query = new HolidayRequestQuery();
        query.setHolidayRequestStatus(HolidayRequestStatus.CREATED);
        query.setCreation_from(LocalDate.of(2021, 9, 12));

        List<HolidayRequestDto> holidayRequestList = getHolidays(query);
        assertThat(holidayRequestList)
                .hasSize(5)
                .extracting(HolidayRequestDto::getHolidayRequestStatus)
                .containsExactlyInAnyOrder(HolidayRequestStatus.CREATED, HolidayRequestStatus.CREATED, HolidayRequestStatus.CREATED,HolidayRequestStatus.CREATED,HolidayRequestStatus.CREATED);
    }

    @Test
    public void testFindHolidays_BYTYPE_AND_VACATION_FROM_TO() {

        //Conditions
        HolidayRequestQuery query = new HolidayRequestQuery();
        LocalDate vacation_from = LocalDate.of(2021, 4, 12);
        query.setVacation_from(vacation_from);
        LocalDate vacation_to = LocalDate.of(2021, 6, 22);
        query.setVacation_to(vacation_to);


        List<HolidayRequestDto> holidayRequestList = getHolidays(query);

        assertThat(holidayRequestList).noneMatch(item ->
                (item.getHolidayStart().isBefore(vacation_from) && item.getHolidayEnd().isBefore(vacation_from))
                        || (item.getHolidayStart().isAfter(vacation_to) && item.getHolidayEnd().isAfter(vacation_to)));


    }


    // helper methods ---------------------------------------------------------

    public List<EmployeeDto> getEmployees() {
        EntityExchangeResult<List<EmployeeDto>> result = webClient.get().uri(uriBuilder ->
                        uriBuilder
                                .path("/api/employee")
                                .queryParam("fullList","true")
                                .queryParam("sort", "employeeName")
                                .build())
                .headers(httpHeaders ->  httpHeaders.setBasicAuth(userName,password))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EmployeeDto.class)
                .returnResult();
        return result.getResponseBody();
    }

    public List<HolidayRequestDto> getHolidays(HolidayRequestQuery query) {


        EntityExchangeResult<List<HolidayRequestDto>> result = webClient.post().uri(uriBuilder ->
                                uriBuilder
                                .path("/api/holidayRequest/find")
                                .build())
                .headers(httpHeaders ->  httpHeaders.setBasicAuth(userName,password))
                .bodyValue(query)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(HolidayRequestDto.class)
                .returnResult();
        return result.getResponseBody();
    }


}
