package com.doittogether.platform.presentation.dto.preset.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record PresetCategoryRegisterRequest (
        @Schema(description = "카테고리 이름", example = "거실")
        String category
) {
}
