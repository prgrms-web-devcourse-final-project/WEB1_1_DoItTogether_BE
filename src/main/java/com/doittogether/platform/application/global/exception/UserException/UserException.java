package com.doittogether.platform.application.global.exception.UserException;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.exception.GlobalException;

public class UserException extends GlobalException {
    public UserException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
