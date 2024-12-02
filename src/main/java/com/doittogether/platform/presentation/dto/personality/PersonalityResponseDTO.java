package com.doittogether.platform.presentation.dto.personality;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record PersonalityResponseDTO(
        @Schema(description = "분석 결과 키워드", example = "[\n"
                + "    \"무난함\uD83D\uDC4C\",\n"
                + "    \"활동적인\uD83C\uDFC3\u200D♀\uFE0F\",\n"
                + "    \"조용함\uD83E\uDD2B\",\n"
                + "    \"스트레스\uD83E\uDD2F\"\n"
                + "  ]")
        List<String> keywords
) {
}
