package com.doittogether.platform.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 공통 에러 Api Response 정의
 *
 * @author ycjung
 */
@Getter
public class ErrorResponse {
    private final boolean isSuccess;
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    // 기본 생성자
    public ErrorResponse(boolean isSuccess, HttpStatus httpStatus, String code, String message) {
        this.isSuccess = isSuccess;
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public ErrorResponse(boolean isSuccess, String code, String message) {
        this(isSuccess, null, code, message);
    }
}
