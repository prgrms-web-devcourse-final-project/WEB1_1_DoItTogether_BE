package com.doittogether.platform.presentation.dto.preset.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "프리셋 카테고리의 삭제 응답")
@Builder
public record PresetCategoryDeleteResponse(
        @Schema(description = "프리셋 카테고리 아이디")
        Long presetCategoryId
) {
    public static PresetCategoryDeleteResponse of(Long presetCategoryId) {
        return PresetCategoryDeleteResponse.builder()
                .presetCategoryId(presetCategoryId)
                .build();
    }
}

