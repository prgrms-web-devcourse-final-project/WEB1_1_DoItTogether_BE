package com.doittogether.platform.presentation.dto.preset.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "프리셋 아이템 수정 요청")
public record PresetItemUpdateRequest(
        @Schema(description = "프리셋 카테고리", example = "청소")
        String category,

        @Schema(description = "수정할 프리셋 소분류 요청 목록", example = "[{\"presetId\": 1, \"name\": \"바닥 청소\"}]")
        List<PresetUpdateRequest> values
) {
}
