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
    USER_CHANNEL_RELATION_NOT_FOUND(HttpStatus.NOT_FOUND, "CHANNEL_404", "유저와 채널 관계를 찾을 수 없습니다."),
    UNABLE_TO_ASSIGN_NEW_ADMIN(HttpStatus.BAD_REQUEST, "CHANNEL_400", "새 관리자를 지정할 수 없습니다."),

    // 초대 링크 관련
    INVALID_INVITE_LINK(HttpStatus.BAD_REQUEST, "INVITE_400", "유효하지 않은 초대 링크입니다."),
    EXPIRED_INVITE_LINK(HttpStatus.GONE, "INVITE_410", "초대 링크가 만료되었습니다."),

    //임시로그인
    TEMPORARY_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "LOGIN_404", "백엔드야!! TemporaryLoginSaveTest에 있는 데이터 넣어라@@@@@!!"),

    // 프리셋 관련
    PRESET_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "PRESET_404", "요청한 프리셋 카테고리를 찾을 수 없습니다."),
    PRESET_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "PRESET_404", "요청한 프리셋 아이템을 찾을 수 없습니다."),
    PRESET_CATEGORY_DUPLICATED(HttpStatus.CONFLICT, "PRESET_409", "이미 존재하는 프리셋 카테고리입니다."),
    PRESET_ITEM_DUPLICATED(HttpStatus.CONFLICT, "PRESET_409", "이미 존재하는 프리셋 아이템입니다."),
    PRESET_CREATION_FAILED(HttpStatus.BAD_REQUEST, "PRESET_400", "프리셋 생성에 실패하였습니다."),
    PRESET_CATEGORY_DELETE_FAILED(HttpStatus.BAD_REQUEST, "PRESET_400", "프리셋 카테고리 삭제에 실패하였습니다."),
    PRESET_ITEM_DELETE_FAILED(HttpStatus.BAD_REQUEST, "PRESET_400", "프리셋 아이템 삭제에 실패하였습니다."),

    // 사용자 관련
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_404", "사용자를 찾을 수 없습니다."),
    USER_ALREADY_IN_CHANNEL(HttpStatus.CONFLICT, "USER_409", "사용자가 이미 채널에 포함되어 있습니다."),
    USER_NOT_IN_CHANNEL(HttpStatus.BAD_REQUEST, "USER_400", "사용자가 채널에 포함되어 있지 않습니다."),

    // 초대 링크 관련
    INVITE_LINK_INVALID(HttpStatus.BAD_REQUEST, "INVITE_400", "유효하지 않거나 만료된 초대 링크입니다."),
    INVITE_LINK_CHANNEL_ID_PARSE_FAILED(HttpStatus.BAD_REQUEST, "INVITE_401", "초대 링크의 채널 ID를 파싱할 수 없습니다."),
    INVITE_LINK_GENERATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "INVITE_500", "초대 링크 생성에 실패하였습니다."),
    REDIS_KEY_SEARCH_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "REDIS_500", "Redis 키 검색 중 오류가 발생하였습니다."),

    //Validation
    INVALID_CHANNEL_ID(HttpStatus.BAD_REQUEST, "VALID_400", "유효하지 않은 채널 ID입니다."),
    INVALID_DATE_FORMAT(HttpStatus.BAD_REQUEST, "VALID_400", "유효하지 않은 날짜 형식입니다."),
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
