package hu.webuni.hrholiday.szabi.web.controller;


import hu.webuni.hrholiday.szabi.dto.LoginDTO;
import hu.webuni.hrholiday.szabi.model.Boss;
import hu.webuni.hrholiday.szabi.model.Employee;
import hu.webuni.hrholiday.szabi.repository.BossRepository;
import hu.webuni.hrholiday.szabi.repository.EmployeeRepository;
import hu.webuni.hrholiday.szabi.service.UserSecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController()
@Component("loginJWTRest")
@RequestMapping("/api/login")
public class LoginJwtController {

    @Autowired
    UserSecurityService userSecurityService;

    @Resource
    AuthenticationManager authenticationManager;

    @Autowired
    EmployeeRepository bossRepository;

    @PostMapping
    public String login (@RequestBody LoginDTO loginDTO){

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUserName(),loginDTO.getPassword()));
        log.debug("Authentication SUCCESSFUL");
        return  userSecurityService.createJwtToken((UserDetails) authentication.getPrincipal());

    }



}
