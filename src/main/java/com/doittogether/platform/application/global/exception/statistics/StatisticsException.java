package com.doittogether.platform.application.global.exception.statistics;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.exception.GlobalException;

public class StatisticsException extends GlobalException {
    public StatisticsException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
