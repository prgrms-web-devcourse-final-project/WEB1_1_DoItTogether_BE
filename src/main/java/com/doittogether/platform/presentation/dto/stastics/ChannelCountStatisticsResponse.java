package com.doittogether.platform.presentation.dto.stastics;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ChannelCountStatisticsResponse(
        @Schema(description = "채널 이름", example = "두투")
        String channelName,

        @NotNull
        @Schema(description = "완료 개수", example = "2")
        Integer completeCount,

        @NotNull
        @Schema(description = "미완료 개수", example = "2")
        Integer notCompleteCount,

        @NotNull
        @Schema(description = "칭찬했어요 개수", example = "3")
        Integer complimentCount,

        @NotNull
        @Schema(description = "찔렸어요 개수", example = "5")
        Integer pokeCount

) {
}
