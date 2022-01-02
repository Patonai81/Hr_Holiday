package hu.webuni.hrholiday.szabi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode

@Entity
@DiscriminatorValue("BOSS_ROLE")
@NamedEntityGraph(name = "Boss.JWT",
        attributeNodes = @NamedAttributeNode("employees")
)
public class Boss extends Employee {

    @OneToMany(mappedBy = "boss")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    List<Employee> employees;


    @EqualsAndHashCode.Exclude public String managerPositionName;

    public Boss(@NonNull String employeeName, @NonNull String userName, @NonNull String password,@NonNull String managerPositionName) {

        super(employeeName,userName,password);
        this.managerPositionName = managerPositionName;
    }

    @Override
    public String toString() {
        return "Boss{" +
                ", managerPositionName='" + managerPositionName + '\'' +
                ", employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", boss=" + boss +
                '}';
    }
}
