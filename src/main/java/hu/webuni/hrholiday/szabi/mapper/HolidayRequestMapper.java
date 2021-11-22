package hu.webuni.hrholiday.szabi.mapper;

import hu.webuni.hrholiday.szabi.dto.HolidayRequestDto;
import hu.webuni.hrholiday.szabi.model.HolidayRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface HolidayRequestMapper {

    @Named("WTH")
    @Mapping(target = "employeeCreator", ignore = true)
    @Mapping(target = "acceptor.employees", ignore = true)
    HolidayRequestDto toHolidayRequestDto(HolidayRequest holidayRequest);


    HolidayRequest toHolidayRequest(HolidayRequestDto holidayRequest);

}
