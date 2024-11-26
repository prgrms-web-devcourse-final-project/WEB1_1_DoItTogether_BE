package com.doittogether.platform.application.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;

public interface BaseResponse<T> {

    @Schema(description = "요청 성공 여부", allowableValues = {"true", "false"}, example = "true")
    boolean isSuccess();

    @Schema(description = "HTTP 상태 코드")
    HttpStatus httpStatus();

    @Schema(description = "응답 코드(비즈니스 로직에서 정의된 코드)", example = "COMMON_200")
    String code();

    @Schema(description = "응답 메세지(비즈니스 로직에서 정의된 메세지)", example = "성공입니다")
    String message();

    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "응답 데이터 (제네릭: 다양 형태의 응답 제공)")
    T result();
}
