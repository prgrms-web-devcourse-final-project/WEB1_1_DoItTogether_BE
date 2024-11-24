package com.doittogether.platform.presentation.dto.preset.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "프리셋 수정 요청")
public record PresetUpdateRequest(
        @NotNull(message = "수정할 프리셋 아이디는 필수 입력 값입니다.")
        @Positive(message = "프리셋 아이디는 양수여야 합니다.")
        @Schema(description = "수정할 프리셋 아이디")
        Long presetId,

        @NotBlank(message = "수정할 프리셋 값은 필수 입력 값입니다.")
        @Schema(description = "수정할 프리셋 값", example = "바닥 청소")
        String value
) {
}
