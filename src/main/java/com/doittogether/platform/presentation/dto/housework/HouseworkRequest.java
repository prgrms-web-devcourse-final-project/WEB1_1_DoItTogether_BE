package com.doittogether.platform.presentation.dto.housework;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Schema(description = "집안일 요청 DTO")
public record HouseworkRequest(
        @NotBlank
        @Schema(description = "카테고리 이름", example = "거실")
        String category,

        @NotBlank
        @Schema(description = "작업 이름", example = "먼지 닦기")
        String task,

        @NotBlank
        @Schema(description = "시작 날짜와 시간", example = "2021-11-30 00:00:00", type = "string")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        LocalDateTime startDateTime,

        @NotBlank
        @Schema(description = "하루 종일 여부", example = "true")
        Boolean isAllDay,

        @NotBlank
        @Schema(description = "작업 담당자 이름", example = "1")
        Long assigneeId
) {
}
