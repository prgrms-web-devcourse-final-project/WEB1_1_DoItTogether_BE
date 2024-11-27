package com.doittogether.platform.application.global.exception.housework;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.exception.GlobalException;

public class HouseworkException extends GlobalException {
    public HouseworkException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
