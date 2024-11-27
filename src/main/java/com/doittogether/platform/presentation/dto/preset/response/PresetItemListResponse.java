package com.doittogether.platform.presentation.dto.preset.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "프리셋 아이템 리스트 응답")
@Builder
public record PresetItemListResponse(
        @Schema(description = "프리셋 아이템 아이디")
        Long presetItemId,

        @Schema(description = "프리셋 카테고리", example = "청소")
        String category,

        @Schema(description = "채널 아이디")
        Long channelId
) {
    public static PresetItemListResponse of(Long presetItemId, String category, Long channelId) {
        return PresetItemListResponse.builder()
                .presetItemId(presetItemId)
                .category(category)
                .channelId(channelId)
                .build();
    }
}
