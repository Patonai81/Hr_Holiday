package hu.webuni.hrholiday.szabi.web.exception;

import lombok.Data;

@Data
public class HolidayRequestQueryNotSuitableException extends RuntimeException{
    private String code;
    private String message;

    public HolidayRequestQueryNotSuitableException(String queryShortNotOk, String message) {
        this.code=queryShortNotOk;
        this.message=message;
    }
}
