package hu.webuni.hrholiday.szabi.dto;


import hu.webuni.hrholiday.szabi.model.Boss;
import hu.webuni.hrholiday.szabi.model.Employee;
import hu.webuni.hrholiday.szabi.model.HolidayRequestStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@RequiredArgsConstructor
public class HolidayRequestDto {

    @EqualsAndHashCode.Include Long holidayRequestId;

    @NotNull
    @NonNull
    @ToString.Exclude
    EmployeeDto employeeCreator;

    @ToString.Exclude
    BossDto acceptor;

    @NotNull
    @NonNull
    LocalDate holidayStart;

    @NotNull
    @NonNull
    LocalDate holidayEnd;

    LocalDateTime creationTimestamp;

    @Enumerated(EnumType.STRING)
    HolidayRequestStatus holidayRequestStatus;

}
