package com.doittogether.platform.application.global.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode implements GlobalCode {
    // 일반적인 공통 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON_400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON_401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON_403", "금지된 요청입니다."),

    // 집안일 등록 관련
    CHORE_NOT_FOUND(HttpStatus.NOT_FOUND, "CHORE_404", "요청한 집안일을 찾을 수 없습니다."),

    //Validation
    NOT_VALIDATE_FILED(HttpStatus.BAD_REQUEST, "VALID_400", "유효성 검증에 실패하였습니다.");
    private final HttpStatus httpStatus; // HTTP 상태 코드
    private final String code; // 응답 코드
    private String message; // 응답 메시지

    ExceptionCode(final HttpStatus httpStatus, final String code, final String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public ExceptionCode withUpdateMessage(String message) {
        this.message = message;
        return this;
    }
}
