package hu.webuni.hrholiday.szabi.web.exception;

import hu.webuni.hrholiday.szabi.model.HolidayRequestStatus;

public class HolidayRequestCannotBeUpdatedException extends RuntimeException{

    private String code;
    private HolidayRequestStatus holidayRequestStatus;

    public HolidayRequestCannotBeUpdatedException(String code, HolidayRequestStatus holidayRequestStatus) {
        this.holidayRequestStatus=holidayRequestStatus;
        this.code=code;
    }

    public String getCode(){
        return code+": "+holidayRequestStatus.name();
    }

}
