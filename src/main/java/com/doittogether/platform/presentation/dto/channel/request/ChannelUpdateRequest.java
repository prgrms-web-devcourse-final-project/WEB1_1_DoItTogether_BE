package com.doittogether.platform.presentation.dto.channel.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "채널 수정 요청")
public record ChannelUpdateRequest (
        @Schema(description = "채널명")
        String name
) {
}
