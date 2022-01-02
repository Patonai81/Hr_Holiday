package hu.webuni.hrholiday.szabi.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Data
@RequiredArgsConstructor

@EqualsAndHashCode
@JsonIgnoreProperties(value = {"holidayRequestsList"})


@Entity(name = "Employee")
@Table(name = "employee")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "WORKER")
@DiscriminatorValue("EMPLOYEE_ROLE")
@NamedEntityGraph(name = "Employee.employeeWithHolidayRequest", attributeNodes = {@NamedAttributeNode(value = "holidayRequestsList")})
public class Employee implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    Long employeeId;


    @OneToMany(mappedBy = "employeeCreator", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    List<HolidayRequest> holidayRequestsList;

    @EqualsAndHashCode.Exclude
    public String employeeName;


    @EqualsAndHashCode.Exclude
    public String username;

    @EqualsAndHashCode.Exclude
    public String password;


    @ManyToOne
    @EqualsAndHashCode.Exclude
    public Boss boss;

    @Transient
    private List<SimpleGrantedAuthority> authorities;

    public Employee(String employeeName, String userName, String password) {
        this.employeeName = employeeName;
        this.username = userName;
        this.password = password;
    }

    public void setAuthorities(List<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
