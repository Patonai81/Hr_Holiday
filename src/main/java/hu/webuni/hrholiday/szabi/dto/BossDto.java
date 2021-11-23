package hu.webuni.hrholiday.szabi.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class BossDto extends EmployeeDto{

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    List<EmployeeDto> employees;


    @EqualsAndHashCode.Exclude private String managerPositionName;

    public BossDto(@NonNull String employeeName, @NonNull String managerPositionName) {
        super(employeeName);
        this.managerPositionName = managerPositionName;
    }
    public BossDto(EmployeeDto employeeDto){
        this.employeeId=employeeDto.employeeId;
        this.employeeName= employeeDto.employeeName;
        this.managerPositionName="BOSS";

    }
}
