package hu.webuni.hrholiday.szabi.web.exceptionHandler;

import hu.webuni.hrholiday.szabi.web.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static hu.webuni.hrholiday.szabi.web.exception.CustomErrorCodes.getMessage;

@RestControllerAdvice
public class HolidayRelatedExceptionHandler {

    @ExceptionHandler({HolidayRequestCannotBeUpdatedException.class})
    public ResponseEntity<ErrorContainer> handleRequestCannotBeUpdated(HolidayRequestCannotBeUpdatedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorContainer(getMessage(e.getCode()), e.getCode()));
    }

    @ExceptionHandler({HolidayRequestQueryNotSuitableException.class})
    public ResponseEntity<ErrorContainer> handleQueryNotSuitable(HolidayRequestQueryNotSuitableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorContainer(e.getMessage(), e.getCode()));
    }

    @ExceptionHandler({HolidayRequestCannotBeFoundException.class})
    public ResponseEntity<ErrorContainer> handleHolidayRequestCannotBeFOund(HolidayRequestCannotBeFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorContainer(getMessage(e.getCode()), e.getCode()));
    }



}
