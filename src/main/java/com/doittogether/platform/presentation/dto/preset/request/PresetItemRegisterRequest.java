package com.doittogether.platform.presentation.dto.preset.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Schema(description = "프리셋 아이템 등록 요청")
public record PresetItemRegisterRequest(
        @NotBlank(message = "프리셋 카테고리는 필수 입력 값입니다.")
        @Schema(description = "프리셋 카테고리", example = "청소")
        String category,

        @Schema(description = "프리셋 소분류 값 목록", example = "[\"바닥 청소\", \"유리창 닦기\"]")
        List<@NotBlank(message = "소분류 값은 빈 값일 수 없습니다.")String> values
) {
}
