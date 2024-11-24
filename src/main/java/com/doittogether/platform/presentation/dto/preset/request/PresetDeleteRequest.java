package com.doittogether.platform.presentation.dto.preset.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

@Schema(description = "프리셋 상세 삭제 요청")
public record PresetDeleteRequest(
        @NotEmpty(message = "삭제할 프리셋 아이디 리스트는 비어 있을 수 없습니다.")
        @Schema(description = "삭제할 프리셋 아이디 리스트")
        List<@NotNull(message = "프리셋 아이디는 null 일 수 없습니다.")
                @Positive(message = "프리셋 아이디는 양수여야 합니다.") Long> presetList
) {
}
