package com.doittogether.platform.presentation.dto.preset.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "프리셋 아이템 등록 요청")
public record PresetItemRegisterRequest(
        @Schema(description = "프리셋 아이템 이름", example = "[\"바닥 청소\", \"유리창 닦기\"]")
        String item
) {
}
