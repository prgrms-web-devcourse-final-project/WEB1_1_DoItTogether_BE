package com.doittogether.platform.application.global;

import com.doittogether.platform.application.global.success.SuccessStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "공통 API 응답 객체")
public class ApiResponse<T> {
    @Schema(description = "요청 성공 여부", allowableValues = {"true", "false"}, example = "true")
    @JsonProperty("isSuccess")
    @Schema(description = "요청 성공 여부", allowableValues = {"true", "false"}, example = "true")
    private final Boolean isSuccess;

    @Schema(description = "HTTP 상태 코드")
    private final HttpStatus httpStatus;
    
    @Schema(description = "응답 코드(비즈니스 로직에서 정의된 코드)", example = "COMMON_200")
    private final String code;

    @Schema(description = "응답 메세지(비즈니스 로직에서 정의된 메세지)", example = "성공입니다")
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "응답 데이터 (제네릭: 다양 형태의 응답 제공)", nullable = true)
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
