package hu.webuni.hrholiday.szabi.web.exception;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
public class ErrorContainer implements Serializable {

    private String errorCode;
    private String errorMessage;

    public ErrorContainer(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
