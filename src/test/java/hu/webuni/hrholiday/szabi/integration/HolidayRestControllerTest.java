package hu.webuni.hrholiday.szabi.integration;

import hu.webuni.hrholiday.szabi.dto.BossDto;
import hu.webuni.hrholiday.szabi.dto.EmployeeDto;
import hu.webuni.hrholiday.szabi.dto.HolidayRequestDto;
import hu.webuni.hrholiday.szabi.dto.HolidayRequestQuery;
import hu.webuni.hrholiday.szabi.model.HolidayRequestStatus;
import hu.webuni.hrholiday.szabi.service.InitDBService;
import hu.webuni.hrholiday.szabi.web.exception.ErrorContainer;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class HolidayRestControllerTest {

    @Autowired
    WebTestClient webClient;

    @Autowired
    InitDBService initDBService;

   @BeforeEach
    public void initDb() {
        initDBService.initDb();
    }

    @Test
    public void testCreateEmployeePositive() {

        initDBService.initDb();

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEmployeeName("First Employee");

        webClient.post()
                .uri("/api/employee/createEmployee")
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
                .bodyValue(holidayRequest)
                .exchange()
                .expectBody(HolidayRequestDto.class)
                .value(item -> {
                    assertThat(item.getHolidayRequestStatus()).isEqualTo(HolidayRequestStatus.CREATED);
                });


    }

    //NullPointer váltódik ki a validálás során, amit nem tudok megfelelően kezelni
    public void testCreateHolidayRequestNegative() {

        EmployeeDto employeeDto = getEmployees().get(0);
        HolidayRequestDto holidayRequest = new HolidayRequestDto(employeeDto, null, LocalDate.of(2021, 9, 15));


        webClient.post()
                .uri("/api/holidayRequest")
                .bodyValue(holidayRequest)
                .exchange().expectStatus().isBadRequest().expectBody(ErrorContainer.class);

    }

   // @Test
    @Transactional
    public void testAcceptHolidayRequestPositive() {

        //Acceptor boss
        EmployeeDto employeeFromRepo =  getEmployees().get(1);

        //Conditions
        HolidayRequestQuery query = new HolidayRequestQuery();
        query.setHolidayRequestStatus(HolidayRequestStatus.CREATED);

        List<HolidayRequestDto> holidayRequestList = getHolidays(query);

        HolidayRequestDto holidayRequestDto = holidayRequestList.get(0);
        holidayRequestDto.setAcceptor(new BossDto(employeeFromRepo));
        holidayRequestDto.setHolidayRequestStatus(HolidayRequestStatus.ACCEPTED);


        webClient.post()
                .uri("/api/holidayRequest/manageHolidayRequest")
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
        EmployeeDto employeeFromRepo =  getEmployees().get(1);

        //Conditions
        HolidayRequestQuery query = new HolidayRequestQuery();
        query.setHolidayRequestStatus(HolidayRequestStatus.DENIED);

        List<HolidayRequestDto> holidayRequestList = getHolidays(query);

        HolidayRequestDto holidayRequestDto = holidayRequestList.get(0);
        holidayRequestDto.setAcceptor(new BossDto(employeeFromRepo));
        holidayRequestDto.setHolidayRequestStatus(HolidayRequestStatus.ACCEPTED);


        webClient.post()
                .uri("/api/holidayRequest/manageHolidayRequest")
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
                  .hasSize(3)
                  .extracting(HolidayRequestDto::getHolidayRequestStatus)
                  .containsExactlyInAnyOrder(HolidayRequestStatus.CREATED,HolidayRequestStatus.CREATED,HolidayRequestStatus.CREATED);
    }

     @Test
    public void testFindHolidays_BYTYPE_AND_VACATION_FROM_TO() {

        //Conditions
        HolidayRequestQuery query = new HolidayRequestQuery();
        query.setVacation_from(LocalDate.of(2021, 4, 12));
         query.setVacation_to(LocalDate.of(2021, 6, 22));


        List<HolidayRequestDto> holidayRequestList = getHolidays(query);

         Condition<LocalDate> vacationCondtion = new Condition<LocalDate>(s -> s.isAfter(query.getVacation_from()), "fairy tale start");

         //TODO befejezni
         /*
         assertThat(holidayRequestList)
                .hasSize(2)
                .satisfies(fairyTale);

         */
    }





    // helper methods ---------------------------------------------------------

    public List<EmployeeDto> getEmployees() {
        EntityExchangeResult<List<EmployeeDto>> result = webClient.get().uri(uriBuilder ->
                        uriBuilder
                                .path("/api/employee")
                                .queryParam("sort", "employeeName")
                                .build())
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
                                .queryParam("sort", "employeeName")
                                .build())
                .bodyValue(query)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(HolidayRequestDto.class)
                .returnResult();
        return result.getResponseBody();
    }


}
