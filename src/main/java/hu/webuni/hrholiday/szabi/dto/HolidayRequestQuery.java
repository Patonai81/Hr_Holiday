package hu.webuni.hrholiday.szabi.dto;


import hu.webuni.hrholiday.szabi.model.HolidayRequest;
import hu.webuni.hrholiday.szabi.model.HolidayRequestStatus;
import hu.webuni.hrholiday.szabi.repository.specification.Holiday2Specification;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


@Data
@RequiredArgsConstructor

public class HolidayRequestQuery {

    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = 3;
    private static final Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.ASC;
    private static final String DEFAULT_SORT_ORDER = "acceptor";

    private int page = DEFAULT_PAGE_NUMBER;
    private int size = DEFAULT_PAGE_SIZE;
    private String[] sort = {DEFAULT_SORT_ORDER + "," + DEFAULT_SORT_DIRECTION};
    private HolidayRequestStatus holidayRequestStatus;
    private String acceptorName;
    private String requestorName;
    private LocalDate creation_from;
    private LocalDate creation_to;
    private LocalDate vacation_from;
    private LocalDate vacation_to;


    public Specification<HolidayRequest> toSpecification() {
        return new Holiday2Specification(this);
    }

    public Pageable getPageable() {

        return PageRequest.of(
                page,
                size,
                Sort.by(createSortingList()));
    }

    private List<Sort.Order> createSortingList() {

        List<Sort.Order> sortingList = new LinkedList<>();
        Arrays.stream(sort).forEach(item -> {
                    String[] sorted = item.split(",");
                    Sort.Order order = null;
                    try {
                        order = new Sort.Order(Sort.Direction.fromString(sorted[1]), sorted[0]);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Unable to identify order!");
                        order = new Sort.Order(Sort.Direction.ASC, sorted[0]);
                    }
                    if (Objects.nonNull(order)) {
                        sortingList.add(order);
                    }
                }
        );
        return sortingList;

    }

}
