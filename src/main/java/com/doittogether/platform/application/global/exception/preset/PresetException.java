package com.doittogether.platform.application.global.exception.preset;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.exception.GlobalException;

public class PresetException extends GlobalException {
    public PresetException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
