package com.doittogether.platform.application.global;

import com.doittogether.platform.application.global.success.SuccessStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * 공통 Api Response 정의
 *
 * @author ycjung
 */
@JsonPropertyOrder({ "isSuccess", "httpStatus", "code", "message", "result" })
@Getter
public class ApiResponse<T> {
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL) // 이 설정으로 null 값이 들어오면 자동으로 출력에서 제외된다.
    private final T result;

    private ApiResponse(Boolean isSuccess, HttpStatus httpStatus, String code, String message, T result) {
        this.isSuccess = isSuccess;
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
        this.result = result;
    }

    // 성공한 경우 응답 생성
    public static <T> ResponseEntity<ApiResponse<T>> onSuccess(T result) {
        return ResponseEntity.ok(new ApiResponse<>(true, HttpStatus.OK, SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), result));
    }

    // 커스텀한 성공시 response 반환값
    public static <T> ResponseEntity<ApiResponse<T>> onSuccess(HttpStatus httpStatus, String code, String message, T result) {
        return new ResponseEntity<>(new ApiResponse<>(true, httpStatus, code, message, result), httpStatus);
    }

    // 반환할 result data가 없는 경우
    public static ResponseEntity<ApiResponse<Void>> onSuccess() {
        return ResponseEntity.ok(new ApiResponse<>(true, HttpStatus.OK, SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), null));
    }

    // 실패한 경우 응답 생성
    public static <T> ApiResponse<T> onFailure(HttpStatus httpStatus, String code, String message, T data) {
        return new ApiResponse<>(false, httpStatus, code, message, data);
    }
}
