package hu.webuni.hrholiday.szabi.web.controller.validator;

import hu.webuni.hrholiday.szabi.dto.HolidayRequestDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class HolidayRequestDtoUpdateValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return HolidayRequestDto.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "acceptor.employeeName", "acceptor.employeeName", "Acceptor name is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "holidayRequestStatus", "holidayRequestStatus", "HolidayRequestStatus is required.");
    }
}
