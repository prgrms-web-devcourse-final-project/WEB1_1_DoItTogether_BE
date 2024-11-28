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
    public static PresetKeywordListResponse of(List<PresetKeywordResponse> presetKeywordList) {
        return PresetKeywordListResponse.builder()
                .presetKeywordList(presetKeywordList)
                .build();
    }
}
