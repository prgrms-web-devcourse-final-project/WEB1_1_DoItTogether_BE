package com.doittogether.platform.application.global.code;

import org.springframework.http.HttpStatus;

public interface GlobalCode {
    HttpStatus getHttpStatus();
    String getCode();
    String getMessage();
}
