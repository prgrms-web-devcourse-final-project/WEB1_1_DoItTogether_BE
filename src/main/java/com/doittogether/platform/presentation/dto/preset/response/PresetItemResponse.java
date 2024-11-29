package com.doittogether.platform.presentation.dto.preset.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "프리셋 세부 항목 응답")
@Builder
public record PresetItemResponse(
        @Schema(description = "프리셋 아이템 아이디")
        Long presetItemId,

        @Schema(description = "프리셋 값", example = "바닥 청소")
        String value
) {
    public static PresetItemResponse of(Long preseItemtId, String value) {
        return PresetItemResponse.builder()
                .presetItemId(preseItemtId)
                .value(value)
                .build();
    }
}