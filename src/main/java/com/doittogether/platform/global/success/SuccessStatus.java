package com.doittogether.platform.global.success;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 커스텀 성공 상태 코드 추가
 *
 * @author ycjung
 */
@Getter
public enum SuccessStatus {
    _OK(HttpStatus.OK, "성공입니다."), // 일반적인 성공 응답
    _CREATED(HttpStatus.CREATED, "리소스가 성공적으로 생성되었습니다."), // 리소스가 생성된 경우
    _NO_CONTENT(HttpStatus.NO_CONTENT, "콘텐츠가 없습니다."); // 요청이 성공적으로 처리되었지만, 콘텐츠가 없는 경우

    private final HttpStatus httpStatus;
    private final String message;

    private SuccessStatus(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
