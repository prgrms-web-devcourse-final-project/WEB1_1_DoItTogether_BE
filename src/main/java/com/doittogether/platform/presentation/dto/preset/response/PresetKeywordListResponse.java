package com.doittogether.platform.presentation.dto.preset.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Schema(description = "전체 프리셋 키워드 리스트 응답")
@Builder
public record PresetKeywordListResponse(
        @Schema(description = "프리셋 데이터 리스트")
        List<PresetKeywordResponse> presetKeywordList
) {
    @Schema(description = "프리셋 데이터 항목")
    @Builder
    public static record PresetKeywordResponse(
            @Schema(description = "프리셋 카테고리 아이디")
            Long presetCategoryId,

            @Schema(description = "카테고리 이름", example = "거실")
            String category,
            
            @Schema(description = "프리셋 아이디")
            String presetId,

            @Schema(description = "프리셋 값", example = "쓰레기통1")
            String value
    ) {}

    public static PresetKeywordListResponse of(List<PresetKeywordResponse> presetKeywordList) {
        return PresetKeywordListResponse.builder()
                .presetKeywordList(presetKeywordList)
                .build();
    }
}
