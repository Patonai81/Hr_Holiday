package hu.webuni.hrholiday.szabi.web.controller;


import hu.webuni.hrholiday.szabi.dto.EmployeeDto;
import hu.webuni.hrholiday.szabi.mapper.EmployeeMapper;
import hu.webuni.hrholiday.szabi.model.Employee;
import hu.webuni.hrholiday.szabi.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public List<EmployeeDto> findAllEmployees(@RequestParam(name = "fullList", required = false,defaultValue = "false") Boolean fullList){

        List<Employee> resultList= employeeService.findAllEmployees(fullList);

      if (fullList)
          return  employeeMapper.toEmployeeDtoListWithHolidayRequest(resultList);

        return employeeMapper.toEmployeeDtoListWithoutHolidayRequest(resultList);
    }

}
