package com.doittogether.platform.presentation.personality.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record PersonalityRequestDto(
        @NotNull(message = "설문조사 결과를 입력해주세요.")
        @Schema(description = "설문조사 결과", example = " [\n"
                + "      \" 물건이 잘 정리되지 않아도 크게 신경 쓰지 않습니다.\",\n"
                + "      \"움직이면서 활동적인 작업을 하는 것을 좋아합니다.\",\n"
                + "      \"시끄러운 환경에서는 집중하기 어렵습니다.\",\n"
                + "      \"반복적으로 해야 하는 집안일이 스트레스를 줍니다.\"\n"
                + "       ]")
        List<String> surveyResultText
) {
}
