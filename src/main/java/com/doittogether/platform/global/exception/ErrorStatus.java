package com.doittogether.platform.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;

/**
 * 커스텀 에러 상태 코드 추가
 *
 * @author ycjung
 */
@Getter
public enum ErrorStatus implements ErrorResponse {
    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    private ErrorStatus(HttpStatus httpStatus, String code, String message){
        this.httpStatus=httpStatus;
        this.code=code;
        this.message=message;
    }

}
