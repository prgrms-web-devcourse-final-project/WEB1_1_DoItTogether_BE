package com.doittogether.platform.presentation.dto.preset.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "프리셋 아이템의 등록 응답")
@Builder
public record PresetItemRegisterResponse(
        @Schema(description = "채널 아이디")
        Long channelId,

        @Schema(description = "프리셋 아이템 아이디")
        Long presetItemId,

        @Schema(description = "프리셋 카테고리", example = "청소")
        String category
) {
    public static PresetItemRegisterResponse of(Long channelId, Long presetItemId, String category) {
        return PresetItemRegisterResponse.builder()
                .channelId(channelId)
                .presetItemId(presetItemId)
                .category(category)
                .build();
   }
}
