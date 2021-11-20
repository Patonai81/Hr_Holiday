package hu.webuni.hrholiday.szabi.model;

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
    @EqualsAndHashCode.Exclude private String department;
}