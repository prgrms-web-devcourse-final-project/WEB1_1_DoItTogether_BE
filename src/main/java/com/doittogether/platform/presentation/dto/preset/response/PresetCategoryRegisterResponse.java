package com.doittogether.platform.presentation.dto.preset.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record PresetCategoryRegisterResponse (
        @Schema(description = "프리셋 카테고리 아이디")
        Long presetCategoryId,

        @Schema(description = "카테고리 이름", example = "거실")
        String category
) {
}
