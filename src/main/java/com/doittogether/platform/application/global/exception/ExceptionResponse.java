package com.doittogether.platform.application.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class ExceptionResponse {
    private final boolean isSuccess;
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    public ExceptionResponse(final boolean isSuccess, final String code, final String message) {
        this(isSuccess, null, code, message);
    }
}
