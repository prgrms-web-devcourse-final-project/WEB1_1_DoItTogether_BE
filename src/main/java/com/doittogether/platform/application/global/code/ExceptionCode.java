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

    // 채널 관련
    CHANNEL_NOT_FOUND(HttpStatus.NOT_FOUND, "CHANNEL_404", "해당 채널을 찾을 수 없습니다."),
    CHANNEL_NAME_DUPLICATE(HttpStatus.CONFLICT, "CHANNEL_409", "채널 이름이 중복됩니다."),
    CHANNEL_ACCESS_DENIED(HttpStatus.FORBIDDEN, "CHANNEL_403", "채널에 대한 접근 권한이 없습니다."),

    // 초대 링크 관련
    INVALID_INVITE_LINK(HttpStatus.BAD_REQUEST, "INVITE_400", "유효하지 않은 초대 링크입니다."),
    EXPIRED_INVITE_LINK(HttpStatus.GONE, "INVITE_410", "초대 링크가 만료되었습니다."),

    //임시로그인
    TEMPORARY_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "LOGIN_404", "백엔드야!! TemporaryLoginSaveTest에 있는 데이터 넣어라@@@@@!!"),

    // 사용자 관련
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_404", "사용자를 찾을 수 없습니다."),
    USER_ALREADY_IN_CHANNEL(HttpStatus.CONFLICT, "USER_409", "사용자가 이미 채널에 포함되어 있습니다."),
    USER_NOT_IN_CHANNEL(HttpStatus.BAD_REQUEST, "USER_400", "사용자가 채널에 포함되어 있지 않습니다."),

    //Validation
    INVALID_CHANNEL_ID(HttpStatus.BAD_REQUEST, "VALID_400", "유효하지 않은 채널 ID입니다."),
    INVALID_DATE_FORMAT(HttpStatus.BAD_REQUEST, "VALID_400", "유효하지 않은 날짜 형식입니다."),
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
