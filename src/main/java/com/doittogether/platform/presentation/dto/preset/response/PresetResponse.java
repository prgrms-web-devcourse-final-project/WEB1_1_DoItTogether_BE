package com.doittogether.platform.presentation.dto.preset.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "프리셋 세부 항목 응답")
@Builder
public record PresetResponse(
        @Schema(description = "프리셋 아이디")
        Long presetId,

        @Schema(description = "프리셋 값", example = "바닥 청소")
        String value
) {
    public static PresetResponse of(Long presetId, String value) {
        return PresetResponse.builder()
                .presetId(presetId)
                .value(value)
                .build();
    }
}