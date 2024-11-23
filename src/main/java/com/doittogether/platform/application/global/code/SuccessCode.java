package com.doittogether.platform.application.global.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SuccessCode implements GlobalCode {
    _OK(HttpStatus.OK, "COMMON_200", "성공입니다."),
    _CREATED(HttpStatus.CREATED, "COMMON_201", "리소스가 성공적으로 생성되었습니다."),
    _NO_CONTENT(HttpStatus.NO_CONTENT, "COMMON_204", "콘텐츠가 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    SuccessCode(final HttpStatus httpStatus, final String code, final String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
