package com.doittogether.platform.presentation.dto.preset.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Schema(description = "특정 카테고리의 프리셋 리스트 응답")
@Builder
public record CategoryPresetResponse(
        @Schema(description = "프리셋 카테고리 아이디")
        Long presetCategoryId,

        @Schema(description = "카테고리 이름", example = "거실")
        String category,

        @Schema(description = "프리셋 값 리스트")
        List<PresetResponse> presetList
) {
    public static CategoryPresetResponse of(String category, List<PresetResponse> presetList) {
        return CategoryPresetResponse.builder()
                .category(category)
                .presetList(presetList)
                .build();
    }
}
