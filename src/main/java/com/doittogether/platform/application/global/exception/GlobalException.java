package com.doittogether.platform.application.global.exception;

import com.doittogether.platform.application.global.code.ExceptionCode;
import lombok.Getter;

@Getter
public abstract class GlobalException extends RuntimeException {
    private final ExceptionCode exceptionCode;

    public GlobalException(final ExceptionCode errorCode) {
        super(errorCode.getMessage());
        this.exceptionCode = errorCode;
    }
}
