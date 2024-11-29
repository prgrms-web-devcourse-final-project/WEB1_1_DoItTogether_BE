package com.doittogether.platform.presentation.dto.preset.response;

import com.doittogether.platform.domain.entity.PresetCategory;
import com.doittogether.platform.domain.entity.PresetItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "프리셋 아이템의 등록 응답")
@Builder
public record PresetItemRegisterResponse(
        @Schema(description = "프리셋 카테고리 아이디")
        Long presetCategoryId,

        @Schema(description = "프리셋 아이템 아이디")
        Long presetItemId,

        @Schema(description = "프리셋 아이템 카테고리", example = "청소")
        String category
) {
    public static PresetItemRegisterResponse of(PresetCategory presetCategory, PresetItem presetItem) {
        return PresetItemRegisterResponse.builder()
                .presetCategoryId(presetCategory.getPresetCategoryId())
                .presetItemId(presetItem.getPresetItemId())
                .category(presetItem.getValue())
                .build();
   }
}
