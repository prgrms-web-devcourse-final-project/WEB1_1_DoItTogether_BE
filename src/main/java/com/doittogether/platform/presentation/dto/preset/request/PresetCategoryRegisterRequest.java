package com.doittogether.platform.presentation.dto.preset.request;

import com.doittogether.platform.domain.entity.Channel;
import com.doittogether.platform.domain.entity.PresetCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "프리셋 카테고리 등록 요청")
@Builder
public record PresetCategoryRegisterRequest(
        @Schema(description = "카테고리 이름", example = "거실")
        String category
) {
    public static PresetCategory toEntity(PresetCategoryRegisterRequest request,
                                          Channel channel) {
        return PresetCategory.of(request.category, channel);
    }
}
