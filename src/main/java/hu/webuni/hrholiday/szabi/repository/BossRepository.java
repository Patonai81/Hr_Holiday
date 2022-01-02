package hu.webuni.hrholiday.szabi.repository;

import hu.webuni.hrholiday.szabi.model.Boss;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface BossRepository extends JpaRepository<Boss, Long> {

@EntityGraph(value = "Boss.JWT")
@Query("select b from Boss b where b.employeeId = :id")
public Boss findBossWithEmployees(Long id);



}
