package hu.webuni.hrholiday.szabi.repository.specification;

import hu.webuni.hrholiday.szabi.dto.HolidayRequestQuery;
import hu.webuni.hrholiday.szabi.model.*;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

        /*
        public static Specification<Employee> hasCompany(String companyName) {
 		return (root, cq, cb) -> {
 			root.fetch(Employee_.company, JoinType.LEFT);
 			root.fetch(Employee_.position, JoinType.LEFT);
 			return cb.like(cb.lower(root.get(Employee_.company).get(Company_.name)), (companyName + "%").toLowerCase());
 		};
 		return (root, cq, cb) -> cb.like(cb.lower(root.get(Employee_.company).get(Company_.name)), (companyName + "%").toLowerCase());
 	}

         */

        if (StringUtils.hasText(holidayRequestQuery.getAcceptorName())) {
            predicates.add(criteriaBuilder.like(root.get(HolidayRequest_.acceptor).get(Boss_.employeeName), holidayRequestQuery.getAcceptorName() + "%"));
        }

        if (StringUtils.hasText(holidayRequestQuery.getRequestorName())) {
            predicates.add(criteriaBuilder.like(root.get(HolidayRequest_.employeeCreator).get(Employee_.employeeName), holidayRequestQuery.getRequestorName() + "%"));
        }

        if (Objects.isNull(holidayRequestQuery.getCreation_to())&& Objects.nonNull(holidayRequestQuery.getCreation_from())){
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(HolidayRequest_.creationTimestamp).as(LocalDate.class), holidayRequestQuery.getCreation_from()));
        }
        if (Objects.isNull(holidayRequestQuery.getCreation_from())&& Objects.nonNull(holidayRequestQuery.getCreation_to())){
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(HolidayRequest_.creationTimestamp).as(LocalDate.class),holidayRequestQuery.getCreation_to()));
        }
        if (Objects.nonNull(holidayRequestQuery.getCreation_from()) && Objects.nonNull(holidayRequestQuery.getCreation_to())){
            predicates.add(criteriaBuilder.between(root.get(HolidayRequest_.creationTimestamp).as(LocalDate.class), holidayRequestQuery.getCreation_from(), holidayRequestQuery.getCreation_to()));
        }

        /*
        if (Objects.nonNull(holidayRequestQuery.getCreation_from())) {

            predicates.add(criteriaBuilder.between(root.get(HolidayRequest_.creationTimestamp),
                    LocalDateTime.of(holidayRequestQuery.getCreation_from(), LocalTime.MIN),
                    LocalDateTime.of(holidayRequestQuery.getCreation_to(), LocalTime.MAX)));

       */

        //(StartA <= EndB) and (EndA >= StartB), csak akkor foglalkozunk vele, ha mindkét dátum adott
        if (Objects.nonNull(holidayRequestQuery.getVacation_from()) && Objects.nonNull(holidayRequestQuery.getVacation_to())){
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(HolidayRequest_.holidayEnd), holidayRequestQuery.getVacation_from()));
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(HolidayRequest_.holidayStart), holidayRequestQuery.getVacation_to()));
        }

            return criteriaQuery
                .where(criteriaBuilder.and(predicates.toArray(new Predicate[0])))
                .distinct(true)
                .getRestriction();
    }


}