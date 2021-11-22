package hu.webuni.hrholiday.szabi.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

//@NamedEntityGraph(name = "Company.companyWithEmployees", attributeNodes = {@NamedAttributeNode(value = "employeesList"), @NamedAttributeNode(value = "companyTypeFromDB")})
@Entity(name = "HolidayRequest")
@Table(name = "holidayRequest")
@Data
@NoArgsConstructor
@EqualsAndHashCode

public class HolidayRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include Long holidayRequestId;

    @ManyToOne
    @ToString.Exclude
    Employee employeeCreator;

    @OneToOne
    @ToString.Exclude
    Boss acceptor;

    LocalDate holidayStart;

    LocalDate holidayEnd;

    LocalDateTime creationTimestamp;

    @Enumerated(EnumType.STRING)
    HolidayRequestStatus holidayRequestStatus;


    public HolidayRequest(Employee emp2, LocalDate of, LocalDate of1) {
        this.employeeCreator=emp2;
        this.holidayStart=of;
        this.holidayEnd=of1;
    }
}


