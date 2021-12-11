package hu.webuni.hrholiday.szabi.dto;


import hu.webuni.hrholiday.szabi.model.Boss;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@RequiredArgsConstructor
//@JsonIgnoreProperties(value = { "holidayRequestsList","boss" })

public class EmployeeDto {

    @EqualsAndHashCode.Include Long employeeId;

    @EqualsAndHashCode.Exclude
            @ToString.Exclude
    List<HolidayRequestDto> holidayRequestsList;

    @EqualsAndHashCode.Exclude
    @NonNull
    @NotNull
    protected String employeeName;

    @EqualsAndHashCode.Exclude
    protected BossDto boss;


    @EqualsAndHashCode.Exclude protected String username;


    @EqualsAndHashCode.Exclude protected String password;


}
