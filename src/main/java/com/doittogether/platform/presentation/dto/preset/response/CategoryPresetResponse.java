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

        @Schema(description = "프리셋 아이템 리스트")
        List<PresetItemResponse> presetItemList
) {
    public static CategoryPresetResponse of(String category, List<PresetItemResponse> presetItemList) {
        return CategoryPresetResponse.builder()
                .category(category)
                .presetItemList(presetItemList)
                .build();
    }
}
