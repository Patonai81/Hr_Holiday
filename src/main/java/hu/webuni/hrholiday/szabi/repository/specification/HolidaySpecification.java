package hu.webuni.hrholiday.szabi.repository.specification;

import hu.webuni.hrholiday.szabi.model.HolidayRequest;
import hu.webuni.hrholiday.szabi.model.HolidayRequestStatus;
import hu.webuni.hrholiday.szabi.model.HolidayRequest_;
import org.springframework.data.jpa.domain.Specification;

public class HolidaySpecification {

    public static Specification<HolidayRequest> hasHolidayRequestStatus(HolidayRequestStatus holidayRequestStatus) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(HolidayRequest_.holidayRequestStatus), holidayRequestStatus);
        };
    }


}
