package com.doittogether.platform.presentation.dto.channel.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "채널 조회 요청")
public record ChannelSelectRequest (
        @NotBlank(message = "선택 날짜는 필수 입력 값입니다.")
        @Schema(description = "선택 날짜")
        String targetDate
) {
}
