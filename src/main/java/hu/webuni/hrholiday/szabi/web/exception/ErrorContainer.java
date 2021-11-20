package hu.webuni.hrholiday.szabi.web.exception;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
public class ErrorContainer implements Serializable {


    @NonNull
    private String errorCode;
    @NonNull
    private String errorMessage;
}
