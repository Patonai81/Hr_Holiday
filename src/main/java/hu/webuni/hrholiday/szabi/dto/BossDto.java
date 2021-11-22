package hu.webuni.hrholiday.szabi.dto;

import hu.webuni.hrholiday.szabi.model.Employee;
import lombok.*;

import java.util.List;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BossDto extends EmployeeDto{

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    List<EmployeeDto> employees;

    @NonNull
    @EqualsAndHashCode.Exclude private String managerPositionName;

    public BossDto(@NonNull String employeeName, @NonNull String managerPositionName) {
        super(employeeName);
        this.managerPositionName = managerPositionName;
    }
}
