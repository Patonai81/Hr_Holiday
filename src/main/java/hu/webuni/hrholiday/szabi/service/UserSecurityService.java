package hu.webuni.hrholiday.szabi.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureGenerationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import hu.webuni.hrholiday.szabi.config.JWTConfig;
import hu.webuni.hrholiday.szabi.model.Boss;
import hu.webuni.hrholiday.szabi.model.Employee;
import hu.webuni.hrholiday.szabi.repository.BossRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Slf4j
@Service
public class UserSecurityService {

    public static final String EMPLOYEE_IDS = "employeeIds";
    public static final String EMPLOYEE_NAMES = "employeeNames";
    public static final String BOSS_NAME = "bossName";
    public static final String BOSS_ID = "bossId";
    public static final String LOGIN_NAME = "loginName";
    public static final String ID = "id";
    public static final String DUMMY = "dummy";

    @Autowired
    JWTConfig jwtConfig;

    @Autowired
    BossRepository bossRepository;

    public static final String AUTH = "auth";

    public String getAuthenticatedUserName() {
        return getAuthenticatedEmployee().getEmployeeName();
    }

    public Long getAuthenticatedUserId() {
        return getAuthenticatedEmployee().getEmployeeId();
    }

    public Employee getAuthenticatedEmployee() {
        return (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public String createJwtToken(UserDetails principal) {
        log.debug("Creating JWT Token for: " + principal.getUsername());
        log.debug(principal.toString());
        Employee employee = (Employee) principal;
        Boss boss = null;
        if (employee instanceof Boss) {
            boss = bossRepository.findBossWithEmployees(((Boss) principal).getEmployeeId());
        }

        Algorithm algorithm = getAlgorithm();
        String[] employeeNames = null;
        String[] employeeIds = null;

        if (boss != null) {
            employeeNames = boss.getEmployees().stream().map(Employee::getEmployeeName).toArray(String[]::new);
            employeeIds = boss.getEmployees().stream().map(item -> Long.toString(item.getEmployeeId())).toArray(String[]::new);
        }
        Long bossId = employee.getBoss() == null ? null : employee.getBoss().getEmployeeId();
        String bossName = employee.getBoss() == null ? null : employee.getBoss().getEmployeeName();

        return JWT.create()
                .withSubject(principal.getUsername())
                .withClaim(ID, employee.getEmployeeId())
                .withClaim(LOGIN_NAME, employee.getUsername())
                .withClaim(BOSS_ID, bossId)
                .withClaim(BOSS_NAME, bossName)
                .withArrayClaim(EMPLOYEE_NAMES, employeeNames)
                .withArrayClaim(EMPLOYEE_IDS, employeeIds)
                .withArrayClaim(AUTH, principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(jwtConfig.getValidity())))
                .withIssuer(jwtConfig.getIssuer())
                .sign(algorithm);
    }

    public Employee decodeJWT(String JWT_token) {

        DecodedJWT decodedJWT = JWT.require(getAlgorithm())
                .withIssuer(jwtConfig.getIssuer())
                .build().verify(JWT_token);

        Boss employee = new Boss();
        employee.setEmployeeId(decodedJWT.getClaim(ID).asLong());
        employee.setUsername(decodedJWT.getSubject());
        employee.setPassword(DUMMY);

        employee.setAuthorities(decodedJWT.getClaim(AUTH).asList(String.class).stream().map(SimpleGrantedAuthority::new).toList());

        if (decodedJWT.getClaim(BOSS_ID) != null) {
            Boss boss = new Boss();
            boss.setEmployeeName(decodedJWT.getClaim(BOSS_NAME).asString());
            boss.setEmployeeId(decodedJWT.getClaim(BOSS_ID).asLong());
            employee.setBoss(boss);
        }


        if (decodedJWT.getClaim(EMPLOYEE_NAMES).asList(String.class) != null) {
            List<Employee> employees = new ArrayList<>();
            AtomicInteger i = new AtomicInteger(0);
            List<String> employeeNames = decodedJWT.getClaim(EMPLOYEE_NAMES).asList(String.class);
            decodedJWT.getClaim(EMPLOYEE_IDS).asList(Long.class).stream().forEach(id -> {

                Employee employee1 = new Employee();
                employee1.setEmployeeId(id);
                employee1.setEmployeeName(employeeNames.get(i.getAndIncrement()));
                employees.add(employee1);

            });
            employee.setEmployees(employees);

        }

        return employee;
    }

    private Algorithm getAlgorithm() {
        Algorithm algorithm = null;
        try {
            Method method = Algorithm.class.getDeclaredMethod(jwtConfig.getAlgorythm(), String.class);
            algorithm = (Algorithm) method.invoke(null, jwtConfig.getSecret());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        if (algorithm == null) {
            log.debug("Using default fallback algorythm");
        }
        algorithm = Algorithm.HMAC256(jwtConfig.getSecret());
        return algorithm;
    }


}
