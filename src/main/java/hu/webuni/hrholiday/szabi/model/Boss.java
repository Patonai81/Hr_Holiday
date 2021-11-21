package hu.webuni.hrholiday.szabi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

@Entity
@DiscriminatorValue("BOSS_ROLE")
public class Boss extends Employee {

    @OneToMany(mappedBy = "boss")
    @EqualsAndHashCode.Exclude List<Employee> employees;

    @NonNull
    @EqualsAndHashCode.Exclude private String managerPositionName;

    public Boss(@NonNull String employeeName, @NonNull String managerPositionName) {
        super(employeeName);
        this.managerPositionName = managerPositionName;
    }
}
