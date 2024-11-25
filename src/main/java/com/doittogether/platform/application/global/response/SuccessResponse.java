package com.doittogether.platform.application.global.response;

import static lombok.AccessLevel.PRIVATE;

import com.doittogether.platform.application.global.code.SuccessCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder(access = PRIVATE)
public record SuccessResponse<T>(
        @Schema(description = "성공 여부", example = "true")
        boolean isSuccess,

        @Schema(description = "HTTP 상태 코드", example = "OK")
        HttpStatus httpStatus,

        @Schema(description = "성공 코드", example = "COMMON_200")
        String code,

        @Schema(description = "성공 메시지", example = "요청이 정상 처리되었습니다.")
        String message,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        T result
) implements BaseResponse<T> {

    public static <T> SuccessResponse<T> onSuccess(final SuccessCode successCode, T result) {
        return SuccessResponse.<T>builder()
                .isSuccess(true)
                .message(successCode.getMessage())
                .result(result)
                .build();
    }

    public static SuccessResponse<Void> onSuccess(final SuccessCode successCode) {
        SuccessResponse<Void> successResponse =SuccessResponse.<Void>builder()
                .isSuccess(true)
                .message(successCode.getMessage())
                .build();
        System.out.println(successResponse.result);
        return successResponse;
    }

    public static BaseResponse<Void> onSuccess() {
        return SuccessResponse.<Void>builder()
                .isSuccess(true)
                .message(SuccessCode._OK.getMessage())
                .build();
    }
}