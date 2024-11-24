package com.doittogether.platform.presentation.dto.preset.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "프리셋 수정 요청")
public record PresetUpdateRequest(
        @Schema(description = "수정할 프리셋 ID", example = "1")
        Long presetId,

        @Schema(description = "수정할 프리셋 값", example = "바닥 청소")
        String value
) {
}
