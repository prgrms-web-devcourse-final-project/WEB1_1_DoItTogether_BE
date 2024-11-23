package com.doittogether.platform.presentation.dto.preset.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 프리셋 아이템 수정에 대한 응답
 *
 * @author ycjung
 */
@Schema(description = "프리셋 아이템의 수정 응답")
public record PresetItemUpdateResponse(
        @Schema(description = "채널 아이디")
        Long channelId,

        @Schema(description = "프리셋 아이템 아이디")
        Long presetItemId
) {
    public static PresetItemUpdateResponse from(Long channelId, Long presetItemId) {
        return new PresetItemUpdateResponse(channelId, presetItemId);
    }
}
