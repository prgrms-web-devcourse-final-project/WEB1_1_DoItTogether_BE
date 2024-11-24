package com.doittogether.platform.presentation.dto.preset.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Schema(description = "프리셋 아이템 수정 요청")
public record PresetItemUpdateRequest(
        @NotBlank(message = "프리셋 카테고리는 필수 입력 값입니다.")
        @Schema(description = "프리셋 카테고리", example = "청소")
        String category,

        @NotEmpty(message = "프리셋 소분류 값 목록은 필수 입력 값입니다.")
        @Schema(description = "수정할 프리셋 소분류 요청 목록", example = "[{\"presetId\": 1, \"name\": \"바닥 청소\"}]")
        List<PresetUpdateRequest> values
) {
}
