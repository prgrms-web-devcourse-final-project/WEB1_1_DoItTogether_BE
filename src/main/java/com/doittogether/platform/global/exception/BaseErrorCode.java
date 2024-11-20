package com.doittogether.platform.global.exception;

/**
 * 사용자 정의한 예외 코드를 얻기 위한 메소드 제공 인터페이스
 * Enum 타입은 메소드를 갖을 수 없기 때문
 *
 * @author ycjung
 */
public interface BaseErrorCode {
    ErrorResponse getResponseWithHttpStatus(); // 메세지, 결과, http 상태 코드까지
}
