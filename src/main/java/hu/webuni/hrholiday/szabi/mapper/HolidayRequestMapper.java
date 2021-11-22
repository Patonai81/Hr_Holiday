package hu.webuni.hrholiday.szabi.mapper;

import hu.webuni.hrholiday.szabi.dto.EmployeeDto;
import hu.webuni.hrholiday.szabi.dto.HolidayRequestDto;
import hu.webuni.hrholiday.szabi.model.Employee;
import hu.webuni.hrholiday.szabi.model.HolidayRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface HolidayRequestMapper {

    @Named("WTH")
    @Mapping(target = "employeeCreator", qualifiedByName = "HR")
    @Mapping(target = "acceptor.employees", ignore = true)
    HolidayRequestDto toHolidayRequestDto(HolidayRequest holidayRequest);

    @Named("HR")
    @Mapping(target = "boss", ignore = true)
    @Mapping(target = "holidayRequestsList", ignore = true)
    EmployeeDto toEmployeeDto(Employee employee);



    HolidayRequest toHolidayRequest(HolidayRequestDto holidayRequest);

}
