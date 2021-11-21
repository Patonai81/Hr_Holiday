package hu.webuni.hrholiday.szabi.dto;


import hu.webuni.hrholiday.szabi.model.Boss;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@RequiredArgsConstructor
//@JsonIgnoreProperties(value = { "holidayRequestsList","boss" })

public class EmployeeDto {

    @EqualsAndHashCode.Include Long employeeId;

    @EqualsAndHashCode.Exclude
    List<HolidayRequestDto> holidayRequestsList;

    @EqualsAndHashCode.Exclude
    @NonNull
    private String employeeName;

    @EqualsAndHashCode.Exclude
    private Boss boss;

}
