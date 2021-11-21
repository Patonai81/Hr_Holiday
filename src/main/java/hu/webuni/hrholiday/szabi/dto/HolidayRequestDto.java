package hu.webuni.hrholiday.szabi.dto;


import hu.webuni.hrholiday.szabi.model.Boss;
import hu.webuni.hrholiday.szabi.model.Employee;
import hu.webuni.hrholiday.szabi.model.HolidayRequestStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@RequiredArgsConstructor
public class HolidayRequestDto {

    @EqualsAndHashCode.Include Long holidayRequestId;

    @NonNull
    @ToString.Exclude
    EmployeeDto employeeCreator;

    @ToString.Exclude
    BossDto acceptor;

    @NonNull
    LocalDate holidayStart;

    @NonNull
    LocalDate holidayEnd;

    LocalDateTime creationTimestamp;

    @Enumerated(EnumType.STRING)
    HolidayRequestStatus holidayRequestStatus;

}
