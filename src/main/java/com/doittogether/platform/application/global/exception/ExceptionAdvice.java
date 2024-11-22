package com.doittogether.platform.application.global.exception;

import com.doittogether.platform.application.global.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 공통 Exception Advice 정의
 * 모든 상위 에러를 잡아서 이 곳에서 커스텀 Api Response 로 매핑해준다.
 *
 * @author ycjung
 */
@RestControllerAdvice(annotations = {RestController.class})
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleUnexpectedException(Exception e) {
        ApiResponse<Object> body = ApiResponse.onFailure(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "COMMON_500",
                "처리되지 않은 오류가 발생했습니다.",
                null
        );

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 언체크 예외 핸들링
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneralException(GeneralException generalException) {
        ApiResponse<Object> body = ApiResponse.onFailure(
                generalException.getErrorReasonHttpStatus().getHttpStatus(),
                generalException.getErrorReasonHttpStatus().getCode(),
                generalException.getErrorReasonHttpStatus().getMessage(),
                null
        );
        return new ResponseEntity<>(body, generalException.getErrorReasonHttpStatus().getHttpStatus());
    }
}
