package com.doittogether.platform.presentation.dto.preset.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Schema(description = "프리셋 상세 삭제 응답")
@Builder
public record PresetItemDeleteResponse(
        @Schema(description = "채널 아이디")
        Long channelId,

        @Schema(description = "프리셋 카테고리 아이디")
        Long presetCategoryId,

        @Schema(description = "프리셋 아이템 아이디")
        Long presetItemId

) {
    public static PresetItemDeleteResponse of(Long channelId, Long presetCategoryId, Long presetItemId) {
        return PresetItemDeleteResponse.builder()
                .channelId(channelId)
                .presetItemId(presetCategoryId)
                .presetItemId(presetItemId)
                .build();
    }
}
