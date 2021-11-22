package hu.webuni.hrholiday.szabi.mapper;

import hu.webuni.hrholiday.szabi.dto.BossDto;
import hu.webuni.hrholiday.szabi.dto.EmployeeDto;
import hu.webuni.hrholiday.szabi.dto.HolidayRequestDto;
import hu.webuni.hrholiday.szabi.model.Boss;
import hu.webuni.hrholiday.szabi.model.Employee;
import hu.webuni.hrholiday.szabi.model.HolidayRequest;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {


    @Named("WTH")
    @IterableMapping(qualifiedByName = "WTH")
    List<EmployeeDto> toEmployeeDtoListWithHolidayRequest(List<Employee> employeeList);

    @Named("WTH")
    default  EmployeeDto toEmployeeDto(Employee employee) {
        if (employee instanceof Boss) {
            return this.toBossDtoWith((Boss) employee);
        } else if (employee instanceof Employee) {
            return this.toEmployeeDtoWith(employee);
        }
        return null;
    }


    @Named("WTHW")
    @Mapping(target = "boss", ignore = true)
    @Mapping(target = "holidayRequestsList", qualifiedByName = "WTH")
    EmployeeDto toEmployeeDtoWith(Employee employee);

    @Named("WTH")
    @Mapping(target = "employees", ignore = true)
    @Mapping(target = "holidayRequestsList", qualifiedByName = "WTH")
    BossDto toBossDtoWith(Boss boss);

    @Named("WTH")
    @Mapping(target = "employeeCreator", ignore = true)
    @Mapping(target = "acceptor.employees", ignore = true)
    HolidayRequestDto toHolidayRequestDto(HolidayRequest holidayRequest);


// ONLY EMPLOYEE


    @Named("OE")
    @IterableMapping(qualifiedByName = "OE")
    List<EmployeeDto> toEmployeeDtoListWithoutHolidayRequest(List<Employee> employeeList);

    @Named("OE")
    default  EmployeeDto toEmployeeDtoOnly(Employee employee) {
        if (employee instanceof Boss) {
            return this.toBossDtoWithout((Boss) employee);
        } else if (employee instanceof Employee) {
            return this.toEmployeeDtoWithout(employee);
        }
        return null;
    }

    @Named("OES")
    @Mapping(target = "holidayRequestsList", ignore = true)
    @Mapping(target = "boss", ignore = true)
    EmployeeDto toEmployeeDtoWithout(Employee employee);

    @Named("OE")
    @Mapping(target = "employees", ignore = true)
    @Mapping(target = "holidayRequestsList", ignore = true)
    BossDto toBossDtoWithout(Boss boss);


    Employee toEmployee(EmployeeDto employeeDto);


}