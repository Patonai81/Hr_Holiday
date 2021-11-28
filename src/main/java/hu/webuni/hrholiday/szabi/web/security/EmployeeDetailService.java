package hu.webuni.hrholiday.szabi.web.security;

import hu.webuni.hrholiday.szabi.repository.EmployeeRepository;
import hu.webuni.hrholiday.szabi.web.exception.CustomErrorCodes;
import hu.webuni.hrholiday.szabi.web.exception.EmployeeCannotBeFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmployeeDetailService implements UserDetailsService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return employeeRepository.findEmployeeByUsername(username).orElseThrow(() -> new EmployeeCannotBeFoundException(CustomErrorCodes.CUSTOMER_USERNAME_NOT_FOUND));
    }
}
