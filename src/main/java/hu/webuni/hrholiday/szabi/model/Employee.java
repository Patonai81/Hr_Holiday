package hu.webuni.hrholiday.szabi.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
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
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include Long employeeId;

    @OneToMany(mappedBy = "employeeCreator", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude List<HolidayRequest> holidayRequestsList;

    @NonNull
    @Column(name = "Name")
    @EqualsAndHashCode.Exclude
    private String employeeName;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    private Boss boss;
}
