package com.doittogether.platform.presentation.dto.stastics;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PersonalCompleteScoreResponse(
        @NotBlank
        @Schema(description = "사용자 닉네임", example = "민재")
        String nickName,

        @NotNull
        @Schema(description = "완료 개수", example = "10")
        Integer completeCount

) {
}
