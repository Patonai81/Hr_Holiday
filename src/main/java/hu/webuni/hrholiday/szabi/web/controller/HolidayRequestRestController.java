package hu.webuni.hrholiday.szabi.web.controller;

import hu.webuni.hrholiday.szabi.dto.HolidayRequestDto;
import hu.webuni.hrholiday.szabi.dto.HolidayRequestQuery;
import hu.webuni.hrholiday.szabi.mapper.HolidayRequestMapper;
import hu.webuni.hrholiday.szabi.model.Employee;
import hu.webuni.hrholiday.szabi.model.HolidayRequest;
import hu.webuni.hrholiday.szabi.service.HolidayRequestService;
import hu.webuni.hrholiday.szabi.web.controller.validator.HolidayRequestDtoUpdateValidator;
import hu.webuni.hrholiday.szabi.web.controller.validator.HolidayRequestQueryValidator;
import hu.webuni.hrholiday.szabi.web.exception.CustomErrorCodes;
import hu.webuni.hrholiday.szabi.web.exception.HolidayRequestCannotBeUpdatedException;
import hu.webuni.hrholiday.szabi.web.exception.HolidayRequestQueryNotSuitableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController()
@Component("holidayRequestRest")
@RequestMapping("/api/holidayRequest")
public class HolidayRequestRestController {


    @Autowired
    HolidayRequestService holidayRequestService;

    @Autowired
    HolidayRequestMapper holidayRequestMapper;

    @Autowired
    HolidayRequestDtoUpdateValidator holidayRequestDtoUpdateValidator;

    @Autowired
    HolidayRequestQueryValidator holidayRequestQueryValidator;


    @PostMapping("/find")
    List<HolidayRequestDto> findHolidayRequest(@RequestBody HolidayRequestQuery holidayRequestQuery, Pageable pageable, BindingResult bindingResult){
        holidayRequestQueryValidator.validate(holidayRequestQuery,bindingResult);
        if (bindingResult.hasErrors())
            throw new HolidayRequestQueryNotSuitableException(CustomErrorCodes.QUERY_SHORT_NOT_OK,bindingResult.getFieldError("sort").getDefaultMessage());
        holidayRequestQuery.setPageable(pageable);
        List<HolidayRequest> holidayRequestPage = holidayRequestService.findHolidayRequestsBy(holidayRequestQuery);
          return holidayRequestPage.stream().map(holidayRequestMapper::toHolidayRequestDto).collect(Collectors.toList());
    }

    @PostMapping
    HolidayRequestDto createHolidayRequest(@RequestBody @Valid HolidayRequestDto holidayRequestDto){
        return holidayRequestMapper.toHolidayRequestDto(holidayRequestService.createHolidayRequest(holidayRequestMapper.toHolidayRequest(holidayRequestDto)));
    }

    @PostMapping("/manageHolidayRequest")
    HolidayRequestDto modifyRequest(@RequestBody HolidayRequestDto holidayRequestDto, BindingResult result){

        holidayRequestDtoUpdateValidator.validate(holidayRequestDto,result);
        if (result.hasErrors()){
            throw new HolidayRequestCannotBeUpdatedException(CustomErrorCodes.HOLIDAY_NOT_MODIFY, holidayRequestDto.getHolidayRequestStatus());
        }
        return holidayRequestMapper.toHolidayRequestDto(holidayRequestService.updateHolidayRequest(holidayRequestMapper.toHolidayRequest(holidayRequestDto)));
    }

}
