package com.doittogether.platform.presentation.dto.preset.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 프리셋 수정에 대한 요청 매핑
 * 특정 presetId와 수정할 이름(name)을 포함합니다.
 *
 * @author ycjung
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "프리셋 수정 요청")
public record PresetUpdateRequest(
        @Schema(description = "수정할 프리셋 ID", example = "1")
        Long presetId,

        @Schema(description = "수정할 프리셋 값", example = "바닥 청소")
        String value
) {
}
