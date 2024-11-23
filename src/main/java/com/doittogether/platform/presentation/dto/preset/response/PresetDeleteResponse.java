package com.doittogether.platform.presentation.dto.preset.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * 프리셋 리스트 삭제에 대한 응답(detail)
 *
 * @author ycjung
 */
@Schema(description = "프리셋 상세 삭제 응답")
public record PresetDeleteResponse(
        @Schema(description = "채널 아이디")
        Long channelId,

        @Schema(description = "프리셋 아이템 아이디")
        Long presetItemId,

        @Schema(description = "삭제된 프리셋 아이디 리스트")
        List<Long> presetList
) {
    public static PresetDeleteResponse from(Long channelId, Long presetItemId, List<Long> presetList) {
        return new PresetDeleteResponse(channelId, presetItemId, presetList);
    }
}
