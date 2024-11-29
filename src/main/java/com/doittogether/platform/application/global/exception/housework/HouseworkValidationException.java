package com.doittogether.platform.application.global.exception.housework;

import com.doittogether.platform.application.global.code.ExceptionCode;

public class HouseworkValidationException extends HouseworkException {
    public HouseworkValidationException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
