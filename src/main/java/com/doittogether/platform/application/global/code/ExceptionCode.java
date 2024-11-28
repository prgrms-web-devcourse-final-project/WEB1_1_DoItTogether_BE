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

    //임시로그인
    TEMPORARY_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "LOGIN_404", "백엔드야!! TemporaryLoginSaveTest에 있는 데이터 넣어라@@@@@!!"),

    // 초대 링크 관련
    INVITE_LINK_INVALID(HttpStatus.BAD_REQUEST, "INVITE_400", "유효하지 않거나 만료된 초대 링크입니다."),
    INVITE_LINK_CHANNEL_ID_PARSE_FAILED(HttpStatus.BAD_REQUEST, "INVITE_401", "초대 링크의 채널 ID를 파싱할 수 없습니다."),
    INVITE_LINK_GENERATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "INVITE_500", "초대 링크 생성에 실패하였습니다."),
    REDIS_KEY_SEARCH_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "REDIS_500", "Redis 키 검색 중 오류가 발생하였습니다."),

    //Validation
    NOT_VALIDATE_FILED(HttpStatus.BAD_REQUEST, "VALID_400", "유효성 검증에 실패하였습니다."),

    // Redis 관련 에러 코드
    EMBEDDED_REDIS_START_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "REDIS_500", "Redis 서버 시작에 실패했습니다."),
    EMBEDDED_REDIS_STOP_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "REDIS_501", "Redis 서버 중지에 실패했습니다."),
    EMBEDDED_REDIS_PORT_UNAVAILABLE(HttpStatus.INTERNAL_SERVER_ERROR, "REDIS_502", "사용 가능한 포트를 찾을 수 없습니다."),
    EMBEDDED_REDIS_PROCESS_CHECK_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "REDIS_503", "Redis 프로세스 확인 중 오류가 발생했습니다."),
    EMBEDDED_REDIS_EXECUTABLE_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "REDIS_504", "Redis 실행 파일을 찾을 수 없습니다.");

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
