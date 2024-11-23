package com.doittogether.platform.presentation.dto.channel.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 채널 조회에 대한 요청 매핑
 *
 * @author ycjung
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "채널 조회 요청")
public record ChannelSelectRequest (
        @Schema(description = "선택 날짜")
        String targetDate
) {
}
