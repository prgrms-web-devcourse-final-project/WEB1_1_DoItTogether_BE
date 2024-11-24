package com.doittogether.platform.presentation.dto.preset.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "프리셋 아이템 등록 요청")
public record PresetItemRegisterRequest(
        @Schema(description = "프리셋 카테고리", example = "청소")
        String category,

        @Schema(description = "프리셋 소분류 값 목록", example = "[\"바닥 청소\", \"유리창 닦기\"]")
        List<String> values
) {
}
