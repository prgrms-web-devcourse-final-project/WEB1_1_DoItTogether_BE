package com.doittogether.platform.application.global.exception.redis;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.exception.GlobalException;

public class EmbeddedRedisException extends GlobalException {
    public EmbeddedRedisException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
