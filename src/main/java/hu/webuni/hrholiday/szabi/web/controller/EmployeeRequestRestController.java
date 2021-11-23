package hu.webuni.hrholiday.szabi.web.controller;


import hu.webuni.hrholiday.szabi.dto.BossDto;
import hu.webuni.hrholiday.szabi.dto.EmployeeDto;
import hu.webuni.hrholiday.szabi.mapper.EmployeeMapper;
import hu.webuni.hrholiday.szabi.model.Boss;
import hu.webuni.hrholiday.szabi.model.Employee;
import hu.webuni.hrholiday.szabi.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@Component("employeeRequestRest")
@RequestMapping("/api/employee")
public class EmployeeRequestRestController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeMapper employeeMapper;

    @GetMapping
    public List<EmployeeDto> findAllEmployees(@RequestParam(name = "fullList", required = false, defaultValue = "false") Boolean fullList,
                                              @RequestParam(name = "sort") String sort,
                                              Pageable pageable) {

        List<Employee> resultList = employeeService.findAllEmployees(fullList, pageable);

        if (fullList)
            return employeeMapper.toEmployeeDtoListWithHolidayRequest(resultList);

        return employeeMapper.toEmployeeDtoListWithoutHolidayRequest(resultList);
    }

    @PostMapping("/createEmployee")
    public EmployeeDto createEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
        return employeeMapper.toEmployeeDtoWithout(employeeService.createEmployee(employeeMapper.toEmployee(employeeDto)));
    }

}
