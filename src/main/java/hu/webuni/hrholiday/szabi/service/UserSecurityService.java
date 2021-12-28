package hu.webuni.hrholiday.szabi.service;

import hu.webuni.hrholiday.szabi.model.Employee;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService {

    public String getAuthenticatedUserName(){
        return getAuthenticatedEmployee().getEmployeeName();
    }

    public Long getAuthenticatedUserId(){
        return getAuthenticatedEmployee().getEmployeeId();

    }

    //jelenleg a másik fentebbi 2 specifikusabb metódust használom
    public Employee getAuthenticatedEmployee(){
        return (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
