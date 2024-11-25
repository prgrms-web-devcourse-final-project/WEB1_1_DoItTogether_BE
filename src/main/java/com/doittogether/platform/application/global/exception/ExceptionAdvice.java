package com.doittogether.platform.application.global.exception;

import com.doittogether.platform.application.global.response.BaseResponse;
import com.doittogether.platform.application.global.response.ExceptionResponse;
import com.doittogether.platform.application.global.code.ExceptionCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Void>> handleUnexpectedException(final Exception exception) {
        final ExceptionCode undefinedExceptionCode = ExceptionCode._INTERNAL_SERVER_ERROR;
        final BaseResponse<Void> body = ExceptionResponse.onFailure(undefinedExceptionCode);
        return new ResponseEntity<>(body, undefinedExceptionCode.getHttpStatus());
    }

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<BaseResponse<Void>> handleGeneralException(final GlobalException exception) {
        BaseResponse<Void> body = ExceptionResponse.onFailure(exception.getExceptionCode());
        return new ResponseEntity<>(body, exception.getExceptionCode().getHttpStatus());
    }
}
