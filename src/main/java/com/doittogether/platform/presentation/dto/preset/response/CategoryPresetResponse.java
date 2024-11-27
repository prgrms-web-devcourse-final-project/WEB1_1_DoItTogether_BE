package com.doittogether.platform.presentation.dto.preset.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Schema(description = "특정 카테고리의 프리셋 리스트 응답")
@Builder
public record CategoryPresetResponse(
        @Schema(description = "카테고리 이름", example = "거실")
        String category,

        @Schema(description = "프리셋 값 리스트", example = "[\"쓰레기통1\", \"쓰레기통2\"]")
        List<String> presets
) {
    public static CategoryPresetResponse of(String category, List<String> presets) {
        return CategoryPresetResponse.builder()
                .category(category)
                .presets(presets)
                .build();
    }
}
