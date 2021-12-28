package hu.webuni.hrholiday.szabi.service;

import hu.webuni.hrholiday.szabi.model.Employee;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService {

    public String getAuthenticatedUserName(){
        return getPrincipalObject().getEmployeeName();
    }

    public Long getAuthenticatedUserId(){
        return getPrincipalObject().getEmployeeId();

    }

    private Employee getPrincipalObject(){
        return (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
