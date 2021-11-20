package hu.webuni.hrholiday.szabi.web.exception;

import lombok.Getter;

@Getter
public class HolidayRequestCannotBeFoundException extends RuntimeException {

    private String code;

    public HolidayRequestCannotBeFoundException(String code) {
        this.code = code;
    }
}
