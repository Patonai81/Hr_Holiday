package hu.webuni.hrholiday.szabi.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(value = { "holidayRequestsList" })


@Entity(name = "Employee")
@Table(name = "employee")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "WORKER")
@DiscriminatorValue("EMPLOYEE_ROLE")
@NamedEntityGraph(name = "Employee.employeeWithHolidayRequest", attributeNodes = {@NamedAttributeNode(value = "holidayRequestsList")})
public class Employee implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include Long employeeId;


    @OneToMany(mappedBy = "employeeCreator", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude List<HolidayRequest> holidayRequestsList;

    @NonNull
    @EqualsAndHashCode.Exclude
    private String employeeName;

    @NonNull
    @EqualsAndHashCode.Exclude private String username;
    @NonNull
    @EqualsAndHashCode.Exclude private String password;


    @ManyToOne
    @EqualsAndHashCode.Exclude
    private Boss boss;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //TODO egyenlőre mindenki Employee
        return Arrays.asList(new SimpleGrantedAuthority("User"));
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
}
