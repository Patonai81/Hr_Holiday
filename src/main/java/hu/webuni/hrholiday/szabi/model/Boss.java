package hu.webuni.hrholiday.szabi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode

@Entity
@DiscriminatorValue("BOSS_ROLE")
public class Boss extends Employee {

    @OneToMany(mappedBy = "boss")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    List<Employee> employees;


    @EqualsAndHashCode.Exclude private String managerPositionName;

    public Boss(@NonNull String employeeName, @NonNull String userName, @NonNull String password,@NonNull String managerPositionName) {
        super(employeeName,userName,password);
        this.managerPositionName = managerPositionName;
    }
}
