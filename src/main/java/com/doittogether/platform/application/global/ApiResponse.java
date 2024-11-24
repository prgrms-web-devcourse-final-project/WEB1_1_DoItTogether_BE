package com.doittogether.platform.application.global;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.code.GlobalCode;
import com.doittogether.platform.application.global.code.SuccessCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@JsonPropertyOrder({"isSuccess", "httpStatus", "code", "message", "result"})
public record ApiResponse<T>(
        boolean isSuccess,
        HttpStatus httpStatus,
        String code,
        String message,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        T result
) {
    private ApiResponse(Boolean isSuccess, GlobalCode globalCode, T result) {
        this(
                isSuccess,
                globalCode.getHttpStatus(),
                globalCode.getCode(),
                globalCode.getMessage(),
                result
        );
    }

    public static <T> ResponseEntity<ApiResponse<T>> onSuccess(SuccessCode successStatus, T result) {
        return ResponseEntity.ok(new ApiResponse<>(true, successStatus, result));
    }

    public static ResponseEntity<ApiResponse<Void>> onSuccess() {
        return ResponseEntity.ok(
                new ApiResponse<>(true, SuccessCode._OK, null));
    }

    public static ApiResponse<Void> onFailure(ExceptionCode exceptionCode) {
        return new ApiResponse<>(false, exceptionCode, null);
    }
}
