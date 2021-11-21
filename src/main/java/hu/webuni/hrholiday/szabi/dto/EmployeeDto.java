package hu.webuni.hrholiday.szabi.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hu.webuni.hrholiday.szabi.model.Boss;
import hu.webuni.hrholiday.szabi.model.HolidayRequest;
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
