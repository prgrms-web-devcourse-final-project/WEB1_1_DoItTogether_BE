package com.doittogether.platform.presentation.dto.preset.response;

import com.doittogether.platform.domain.entity.PresetCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "프리셋 카테고리 등록 응답")
@Builder
public record PresetCategoryRegisterResponse (
        @Schema(description = "프리셋 카테고리 아이디")
        Long presetCategoryId,

        @Schema(description = "카테고리 이름", example = "거실")
        String category
) {
        public static PresetCategoryRegisterResponse from(PresetCategory presetCategory) {
                return PresetCategoryRegisterResponse.builder()
                        .presetCategoryId(presetCategory.getPresetCategoryId())
                        .category(presetCategory.getCategory())
                        .build();
        }
}
