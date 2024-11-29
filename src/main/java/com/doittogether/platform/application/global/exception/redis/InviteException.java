package com.doittogether.platform.application.global.exception.redis;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.exception.GlobalException;

public class InviteException extends GlobalException {
    public InviteException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
