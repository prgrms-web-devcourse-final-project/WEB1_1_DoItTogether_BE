package com.doittogether.platform.application.global.success;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 커스텀 성공 상태 코드 추가
 *
 * @author ycjung
 */
@Getter
public enum SuccessStatus {
    _OK(HttpStatus.OK, "COMMON_200", "성공입니다."), // 일반적인 성공 응답
    _CREATED(HttpStatus.CREATED, "COMMON_201", "리소스가 성공적으로 생성되었습니다."), // 리소스 생성
    _NO_CONTENT(HttpStatus.NO_CONTENT, "COMMON_204", "콘텐츠가 없습니다."); // 요청 성공, 콘텐츠 없음

    private final HttpStatus httpStatus; // HTTP 상태 코드
    private final String code; // 응답 코드
    private final String message; // 응답 메시지

    SuccessStatus(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
