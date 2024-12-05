package com.doittogether.platform.application.global.exception.reaction;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.exception.GlobalException;

public class ReactionException extends GlobalException {
    public ReactionException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
