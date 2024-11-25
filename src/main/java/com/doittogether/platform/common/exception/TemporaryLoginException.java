package com.doittogether.platform.common.exception;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.exception.GlobalException;

public class TemporaryLoginException extends GlobalException {
    public TemporaryLoginException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
