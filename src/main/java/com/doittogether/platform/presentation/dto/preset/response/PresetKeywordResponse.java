package com.doittogether.platform.presentation.dto.preset.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "프리셋 데이터 항목")
@Builder
public record PresetKeywordResponse(
        @Schema(description = "프리셋 카테고리 아이디")
        Long presetCategoryId,

        @Schema(description = "카테고리 이름", example = "거실")
        String category,

        @Schema(description = "프리셋 아이디")
        Long presetId,

        @Schema(description = "프리셋 값", example = "쓰레기통1")
        String value
) {}
