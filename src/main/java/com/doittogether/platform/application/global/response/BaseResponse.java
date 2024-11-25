package com.doittogether.platform.application.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

public interface BaseResponse<T> {
    boolean isSuccess();

    HttpStatus httpStatus();

    String code();

    String message();

    T result();
}
