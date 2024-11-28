package com.doittogether.platform.presentation.dto.preset.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "프리셋 카테고리 삭제 응답")
@Builder
public record PresetCategodyDeleteResponse(
        @Schema(description = "삭제된 프리셋 카테고리 아이디")
        Long presetItemId
) {
    public static PresetCategodyDeleteResponse from(Long presetItemId) {
        return PresetCategodyDeleteResponse.builder()
                .presetItemId(presetItemId)
                .build();
    }
}

