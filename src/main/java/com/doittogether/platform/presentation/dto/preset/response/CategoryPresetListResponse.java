package com.doittogether.platform.presentation.dto.preset.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Schema(description = "모든 카테고리의 프리셋 리스트 응답")
@Builder
public record CategoryPresetListResponse (
        @Schema(description = "")
        List<CategoryPresetResponse> presetCategoryList
) {
        public static CategoryPresetListResponse of(List<CategoryPresetResponse> presetCategoryList) {
                return CategoryPresetListResponse.builder()
                        .presetCategoryList(presetCategoryList)
                        .build();
        }
}
