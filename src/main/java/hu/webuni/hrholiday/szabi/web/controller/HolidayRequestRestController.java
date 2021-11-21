package hu.webuni.hrholiday.szabi.web.controller;

import hu.webuni.hrholiday.szabi.dto.HolidayRequestDto;
import hu.webuni.hrholiday.szabi.model.HolidayRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@Component("holidayRequestRest")
@RequestMapping("/api/holidayRequest")
public class HolidayRequestRestController {


    List<HolidayRequestDto> findAllHolidayRequest() {
return null;
    }


}
