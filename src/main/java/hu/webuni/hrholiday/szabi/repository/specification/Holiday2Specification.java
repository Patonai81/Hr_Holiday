package hu.webuni.hrholiday.szabi.repository.specification;

import hu.webuni.hrholiday.szabi.dto.HolidayRequestQuery;
import hu.webuni.hrholiday.szabi.model.*;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class Holiday2Specification implements Specification<HolidayRequest> {

    HolidayRequestQuery holidayRequestQuery;


    @Override
    public javax.persistence.criteria.Predicate toPredicate(Root<HolidayRequest> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new LinkedList<>();
      //  Join<HolidayRequest, Employee> requestEmployeeJoin = root.join(HolidayRequest_.employeeCreator, JoinType.LEFT);
      //  root.fetch(HolidayRequest_.employeeCreator, JoinType.LEFT);

        if (holidayRequestQuery.getHolidayRequestStatus() != null) {
            predicates.add(criteriaBuilder.equal(root.get(HolidayRequest_.holidayRequestStatus), holidayRequestQuery.getHolidayRequestStatus()));
        }

        if (StringUtils.hasText(holidayRequestQuery.getAcceptorName())){
            predicates.add(criteriaBuilder.like(root.get(HolidayRequest_.acceptor).get(Boss_.employeeName), holidayRequestQuery.getAcceptorName()+"%"));
        }

        if (StringUtils.hasText(holidayRequestQuery.getRequestorName())){
            predicates.add(criteriaBuilder.like(root.get(HolidayRequest_.employeeCreator).get(Employee_.employeeName), holidayRequestQuery.getRequestorName()+"%"));
        }

        if (Objects.nonNull(holidayRequestQuery.getCreation_from())){
            predicates.add(criteriaBuilder.between(root.get(HolidayRequest_.creationTimestamp), holidayRequestQuery.getCreation_from(), holidayRequestQuery.getCreation_to());

        }


        return criteriaQuery
                .where(criteriaBuilder.and(predicates.toArray(new Predicate[0])))
                .distinct(true)
                .getRestriction();
    }


}