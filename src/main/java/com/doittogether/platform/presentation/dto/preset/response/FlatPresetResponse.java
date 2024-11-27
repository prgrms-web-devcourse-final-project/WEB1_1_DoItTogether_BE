package com.doittogether.platform.presentation.dto.preset.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Schema(description = "전체 프리셋 키워드 리스트 응답")
@Builder
public record FlatPresetResponse(
        @Schema(description = "프리셋 데이터 리스트")
        List<FlatPreset> flatPresets
) {
    @Schema(description = "프리셋 데이터 항목")
    @Builder
    public static record FlatPreset(
            @Schema(description = "프리셋 카테고리 아이디")
            Long presetCategoryId,

            @Schema(description = "카테고리 이름", example = "거실")
            String category,
            
            @Schema(description = "프리셋 아이디")
            String presetId,

            @Schema(description = "프리셋 값", example = "쓰레기통1")
            String value
    ) {}

    public static FlatPresetResponse of(List<FlatPreset> flatPresets) {
        return FlatPresetResponse.builder()
                .flatPresets(flatPresets)
                .build();
    }
}
