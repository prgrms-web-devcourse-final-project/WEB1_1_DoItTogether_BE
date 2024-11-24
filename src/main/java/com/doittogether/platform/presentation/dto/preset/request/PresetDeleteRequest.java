package com.doittogether.platform.presentation.dto.preset.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "프리셋 상세 삭제 요청")
public record PresetDeleteRequest(
        @Schema(description = "삭제할 프리셋 아이디 리스트")
        List<Long> presetList
) {
}
