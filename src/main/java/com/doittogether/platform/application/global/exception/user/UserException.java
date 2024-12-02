package com.doittogether.platform.application.global.exception.user;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.exception.GlobalException;

public class UserException extends GlobalException {
    public UserException(ExceptionCode errorCode) {
        super(errorCode);
    }
}
