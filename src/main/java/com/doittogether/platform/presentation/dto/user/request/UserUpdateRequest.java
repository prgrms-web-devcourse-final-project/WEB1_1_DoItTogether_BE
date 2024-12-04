package com.doittogether.platform.presentation.dto.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "사용자 프로필 업데이트 요청")
public record UserUpdateRequest(
        @Schema(description = "수정할 닉네임", example = "새로운닉네임", required = true)
        @NotBlank(message = "닉네임은 필수 입력값입니다.(공백 값도 불가)")
        String nickName
) {
}