package hu.webuni.hrholiday.szabi.repository;

import hu.webuni.hrholiday.szabi.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee,Long>, JpaSpecificationExecutor<Employee> {

    @EntityGraph("Employee.employeeWithHolidayRequest")
    @Query("SELECT e from Employee  e")
    public Page<Employee> findAllWithHolidayRequests(Pageable pageable);
}
