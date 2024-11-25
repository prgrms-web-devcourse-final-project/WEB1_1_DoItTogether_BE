package com.doittogether.platform.presentation.dto.preset.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "프리셋 아이템의 수정 응답")
@Builder
public record PresetItemUpdateResponse(
        @Schema(description = "채널 아이디")
        Long channelId,

        @Schema(description = "프리셋 아이템 아이디")
        Long presetItemId
) {
    public static PresetItemUpdateResponse of(Long channelId, Long presetItemId) {
        return PresetItemUpdateResponse.builder()
                .channelId(channelId)
                .presetItemId(presetItemId)
                .build();
    }
}
