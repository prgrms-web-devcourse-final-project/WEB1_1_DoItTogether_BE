package com.doittogether.platform.application.global.exception.channel;

import com.doittogether.platform.application.global.code.ExceptionCode;

public class ChannelValidationException extends ChannelException {
    public ChannelValidationException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
