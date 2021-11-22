package hu.webuni.hrholiday.szabi.web.controller;

import hu.webuni.hrholiday.szabi.dto.HolidayRequestDto;
import hu.webuni.hrholiday.szabi.dto.HolidayRequestQuery;
import hu.webuni.hrholiday.szabi.mapper.EmployeeMapper;
import hu.webuni.hrholiday.szabi.model.HolidayRequest;
import hu.webuni.hrholiday.szabi.service.HolidayRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController()
@Component("holidayRequestRest")
@RequestMapping("/api/holidayRequest")
public class HolidayRequestRestController {


    @Autowired
    HolidayRequestService holidayRequestService;

    @Autowired
    EmployeeMapper employeeMapper;


    @GetMapping("/obj")
    List<HolidayRequestDto> findHolidayRequest(@RequestBody HolidayRequestQuery holidayRequestQuery){
          List<HolidayRequest> holidayRequestPage = holidayRequestService.findHolidayRequestsBy(holidayRequestQuery);
          return holidayRequestPage.stream().map(employeeMapper::toHolidayRequestDto).collect(Collectors.toList());
    }


}
