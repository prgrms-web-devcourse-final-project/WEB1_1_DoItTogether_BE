package com.doittogether.platform.presentation.dto.preset.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Schema(description = "프리셋 아이템 상세 응답")
@Builder
public record PresetItemDetailResponse(
        @Schema(description = "프리셋 아이템 아이디")
        Long presetItemId,

        @Schema(description = "프리셋 카테고리", example = "청소")
        String category,

        @Schema(description = "채널 아이디")
        Long channelId,

        @Schema(description = "프리셋 세부 항목 목록")
        List<PresetResponse> presets
) {
    public static PresetItemDetailResponse of(Long presetItemId, String category, Long channelId, List<PresetResponse> presets) {
        return PresetItemDetailResponse.builder()
                .presetItemId(presetItemId)
                .category(category)
                .channelId(channelId)
                .presets(presets)
                .build();
    }
}
