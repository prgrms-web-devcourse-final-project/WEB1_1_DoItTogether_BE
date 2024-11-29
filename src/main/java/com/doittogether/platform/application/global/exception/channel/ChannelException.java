package com.doittogether.platform.application.global.exception.channel;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.exception.GlobalException;

public class ChannelException extends GlobalException {
    public ChannelException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
