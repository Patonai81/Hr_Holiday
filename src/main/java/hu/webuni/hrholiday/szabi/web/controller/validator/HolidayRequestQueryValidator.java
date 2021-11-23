package hu.webuni.hrholiday.szabi.web.controller.validator;

import hu.webuni.hrholiday.szabi.dto.HolidayRequestQuery;
import hu.webuni.hrholiday.szabi.web.exception.CustomErrorCodes;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Arrays;

import static hu.webuni.hrholiday.szabi.model.HolidayRequest_.*;

@Component
public class HolidayRequestQueryValidator implements Validator {

    private static String[] acceptableShortingFields = {HOLIDAY_START, HOLIDAY_END, ACCEPTOR, EMPLOYEE_CREATOR};

    @Override
    public boolean supports(Class<?> clazz) {
        return HolidayRequestQuery.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        HolidayRequestQuery holidayRequestQuery = (HolidayRequestQuery) target;

        if (!Arrays.stream(holidayRequestQuery.getSort()).allMatch(item -> {
                    String sortingFiledName = item.split(",")[0];
                    return Arrays.stream(acceptableShortingFields).toList().contains(sortingFiledName);
                }
        )

        ) {
            errors.rejectValue("sort", CustomErrorCodes.QUERY_SHORT_NOT_OK, "Given shorting condition is NOT suitable: " + Arrays.asList(holidayRequestQuery.getSort()));
        }
    }
}
