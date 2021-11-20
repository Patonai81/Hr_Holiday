package hu.webuni.hrholiday.szabi.model;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

@Entity(name = "Employee")
@Table(name = "employee")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "WORKER")
@DiscriminatorValue("EMPLOYEE_ROLE")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include Long employeeId;

    @OneToMany(mappedBy = "employee")
    @EqualsAndHashCode.Exclude List<HolidayRequest> holidayRequestsList;

    @NonNull
    @Column(name = "Name")
    @EqualsAndHashCode.Exclude
    private String employeeName;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    private Boss boss;
}
