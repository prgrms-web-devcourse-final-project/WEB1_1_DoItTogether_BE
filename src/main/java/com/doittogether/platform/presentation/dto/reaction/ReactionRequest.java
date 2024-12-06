package com.doittogether.platform.presentation.dto.reaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "알림 요청")
public record ReactionRequest (
        @NotNull
        @Schema(description = "타겟 사용자 ID", example = "1")
        Long targetUserId,

        @NotNull(message = "알림 날짜를 입력해주세요. 패턴은 yyyy-MM-dd과 같습니다.")
        @Schema(description = "알림 날짜", example = "2024-11-23", type = "string")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate reactDate
) {
}
