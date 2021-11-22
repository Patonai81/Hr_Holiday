package hu.webuni.hrholiday.szabi.repository.specification;

import hu.webuni.hrholiday.szabi.dto.HolidayRequestQuery;
import hu.webuni.hrholiday.szabi.model.Employee;
import hu.webuni.hrholiday.szabi.model.HolidayRequest;
import hu.webuni.hrholiday.szabi.model.HolidayRequest_;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
public class Holiday2Specification implements Specification<HolidayRequest> {

    HolidayRequestQuery holidayRequestQuery;


    @Override
    public javax.persistence.criteria.Predicate toPredicate(Root<HolidayRequest> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new LinkedList<>();
      //  Join<HolidayRequest, Employee> requestEmployeeJoin = root.join(HolidayRequest_.employeeCreator, JoinType.LEFT);
        //root.fetch(HolidayRequest_.employeeCreator, JoinType.LEFT);

        if (holidayRequestQuery.getHolidayRequestStatus() != null) {
            predicates.add(criteriaBuilder.equal(root.get(HolidayRequest_.holidayRequestStatus), holidayRequestQuery.getHolidayRequestStatus()));
        }


        return criteriaQuery
                .where(criteriaBuilder.and(predicates.toArray(new Predicate[0])))
                .distinct(true)
                .getRestriction();
    }


}