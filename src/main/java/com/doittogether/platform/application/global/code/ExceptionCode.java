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
    HOUSEWORK_NOT_FOUND(HttpStatus.NOT_FOUND, "HOUSEWORK_404", "요청한 집안일을 찾을 수 없습니다."),
    HOUSEWORK_NOT_NULL(HttpStatus.UNPROCESSABLE_ENTITY, "HOUSEWORK_422", "집안일 객체가 null일 수는 없습니다."),
    HOUSEWORK_NO_PERMISSION(HttpStatus.FORBIDDEN, "HOUSEWORK_403", "해당 집안일에 접근할 권한이 없습니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "CATEGORY_404", "요청한 집안일 카테고리를 찾을 수 없습니다."),


    //임시로그인
    TEMPORARY_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "LOGIN_404", "백엔드야!! TemporaryLoginSaveTest에 있는 데이터 넣어라@@@@@!!"),

    //Validation
    NOT_VALIDATE_FILED(HttpStatus.BAD_REQUEST, "VALID_400", "유효성 검증에 실패하였습니다."),

    //채널
    CHANNEL_NOT_FOUND(HttpStatus.NOT_FOUND, "CHANNEL_404", "해당 채널을 찾지 못하였습니다.");

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
