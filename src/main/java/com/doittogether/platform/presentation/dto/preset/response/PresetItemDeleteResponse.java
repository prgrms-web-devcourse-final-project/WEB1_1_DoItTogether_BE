package com.doittogether.platform.presentation.dto.preset.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "프리셋 아이템 삭제 응답")
@Builder
public record PresetItemDeleteResponse(
        @Schema(description = "삭제된 프리셋 아이템 아이디")
        Long presetItemId
) {
    public static PresetItemDeleteResponse from(Long presetItemId) {
        return PresetItemDeleteResponse.builder()
                .presetItemId(presetItemId)
                .build();
    }
}
