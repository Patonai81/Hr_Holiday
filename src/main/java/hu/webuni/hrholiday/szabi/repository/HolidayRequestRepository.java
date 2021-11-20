package hu.webuni.hrholiday.szabi.repository;


import hu.webuni.hrholiday.szabi.model.HolidayRequest;
import hu.webuni.hrholiday.szabi.model.HolidayRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HolidayRequestRepository extends JpaRepository<HolidayRequest,Long>, JpaSpecificationExecutor<HolidayRequest> {

    @Query("select h from HolidayRequest  h where h.holidayRequestStatus= :holidayRequestStatus")
    public List<HolidayRequest> findHolidayRequestByStatus (HolidayRequestStatus holidayRequestStatus);

}
