package hu.webuni.hrholiday.szabi.web.controller;

import hu.webuni.hrholiday.szabi.dto.HolidayRequestDto;
import hu.webuni.hrholiday.szabi.dto.HolidayRequestQuery;
import hu.webuni.hrholiday.szabi.model.HolidayRequestStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController()
@Component("holidayRequestRest")
@RequestMapping("/api/holidayRequest")
public class HolidayRequestRestController {

    @GetMapping
    List<HolidayRequestDto> findHolidayRequest(@RequestParam(name ="sort",required = false) String sort,
                                               @RequestParam(required = false) String holidayRequestStatus,
                                               @RequestParam(required = false) String acceptorName,
                                               @RequestParam(required = false) String requestorName,
                                               @RequestParam(required = false) LocalDate creation_from,
                                               @RequestParam(required = false) LocalDate creation_to,
                                               @RequestParam(required = false) LocalDate vacation_from,
                                               @RequestParam(required = false) LocalDate vacation_to,
                                               Pageable pageable) {



        System.out.println("Itt repül a kismadár");
        System.out.println(pageable);
        return null;
    }

    @GetMapping("/obj")
    List<HolidayRequestDto> findHolidayRequest(@RequestBody HolidayRequestQuery holidayRequestQuery){


        System.out.println("Itt repül a kismadár2");

        System.out.println(holidayRequestQuery);
        System.out.println("Pageable");
        System.out.println(holidayRequestQuery.getPageable());

        return null;
    }


}
