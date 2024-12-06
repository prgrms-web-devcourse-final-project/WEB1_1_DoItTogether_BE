package com.doittogether.platform.application.global.exception.personality;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.exception.GlobalException;

public class PersonalityException extends GlobalException {
    public PersonalityException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
