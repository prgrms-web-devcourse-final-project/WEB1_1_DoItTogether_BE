package com.doittogether.platform.application.global.exception;

import com.doittogether.platform.application.global.response.ExceptionResponse;
import com.doittogether.platform.application.global.code.ExceptionCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse<Void>> handleUnexpectedException(final Exception exception) {
        final ExceptionCode undefinedExceptionCode = ExceptionCode._INTERNAL_SERVER_ERROR;
        final ExceptionResponse<Void> body = ExceptionResponse.onFailure(undefinedExceptionCode);
        return new ResponseEntity<>(body, undefinedExceptionCode.getHttpStatus());
    }

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ExceptionResponse<Void>> handleGeneralException(final GlobalException exception) {
        final ExceptionResponse<Void> body = ExceptionResponse.onFailure(exception.getExceptionCode());
        return new ResponseEntity<>(body, exception.getExceptionCode().getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse<Void>> handleValidException(final MethodArgumentNotValidException exception) {
        final ExceptionCode validExceptionCode = ExceptionCode.NOT_VALIDATE_FILED;
        final ExceptionCode validExceptionCodeWithFieldMessage = validExceptionCode.withUpdateMessage(exception.getMessage());
        final ExceptionResponse<Void> body = ExceptionResponse.onFailure(validExceptionCodeWithFieldMessage);
        return new ResponseEntity<>(body, validExceptionCodeWithFieldMessage.getHttpStatus());
    }
}
