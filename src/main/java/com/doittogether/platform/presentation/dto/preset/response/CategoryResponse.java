package com.doittogether.platform.presentation.dto.preset.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "카테고리 이름 응답")
@Builder
public record CategoryResponse (
        @Schema(description = "프리셋 카테고리 아이디")
        Long presetCategoryId,

        @Schema(description = "프리셋 카테고리", example = "청소")
        String category
) {
}
