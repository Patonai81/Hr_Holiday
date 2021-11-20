package hu.webuni.hrholiday.szabi.web.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeCannotBeFoundException extends RuntimeException{

    private String code;

    public EmployeeCannotBeFoundException(String code) {
        this.code=code;
    }
}
