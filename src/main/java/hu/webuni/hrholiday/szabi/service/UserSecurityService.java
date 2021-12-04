package hu.webuni.hrholiday.szabi.service;

import hu.webuni.hrholiday.szabi.model.Employee;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService {

    public String getAuthenticatedUserName(){
        Employee authenticatedEmployee = (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return authenticatedEmployee.getEmployeeName();
    }

    public Long getAuthenticatedUserId(){
        Employee authenticatedEmployee = (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return authenticatedEmployee.getEmployeeId();

    }
}
