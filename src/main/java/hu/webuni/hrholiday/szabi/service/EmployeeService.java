package hu.webuni.hrholiday.szabi.service;

import hu.webuni.hrholiday.szabi.model.Employee;
import hu.webuni.hrholiday.szabi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Transactional
    public Employee createEmployee(Employee employee) {
        Employee employeeSaved = employeeRepository.save(employee);
        return employeeSaved;
    }

    public List<Employee> findAllEmployees(Boolean withHolidayRequest, Pageable pageable) {
        if (withHolidayRequest)
            return employeeRepository.findAllWithHolidayRequests(pageable).getContent();

        return employeeRepository.findAll(pageable).getContent();
    }
}
