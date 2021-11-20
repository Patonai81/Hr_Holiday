package hu.webuni.hrholiday.szabi.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

//@NamedEntityGraph(name = "Company.companyWithEmployees", attributeNodes = {@NamedAttributeNode(value = "employeesList"), @NamedAttributeNode(value = "companyTypeFromDB")})
@Entity(name = "HolidayRequest")
@Table(name = "holidayRequest")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class HolidayRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include Long holidayRequestId;

    @ManyToOne
    Employee employee;

    @NonNull
    LocalDate holidayStart;

    @NonNull
    LocalDate holidayEnd;

    @NonNull
    @Enumerated(EnumType.STRING)
    HolidayRequestStatus holidayRequestStatus;
}


