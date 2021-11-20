package hu.webuni.hrholiday.szabi.web.exceptionHandler;

import hu.webuni.hrholiday.szabi.web.exception.EmployeeCannotBeFoundException;
import hu.webuni.hrholiday.szabi.web.exception.ErrorContainer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static hu.webuni.hrholiday.szabi.web.exception.CustomErrorCodes.getMessage;


@RestControllerAdvice
public class EmployeeRelatedExceptionHandler {

    @ExceptionHandler(EmployeeCannotBeFoundException.class)
    public ResponseEntity<ErrorContainer> handleCustomerNotFound(EmployeeCannotBeFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorContainer(getMessage(e.getCode()), e.getCode()));
    }

}
