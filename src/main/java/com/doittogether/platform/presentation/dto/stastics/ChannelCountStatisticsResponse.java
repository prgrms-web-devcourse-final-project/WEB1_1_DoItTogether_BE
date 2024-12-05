package com.doittogether.platform.presentation.dto.stastics;

import com.doittogether.platform.domain.entity.Channel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Map;

@Builder
public record ChannelCountStatisticsResponse(
        @Schema(description = "채널 이름", example = "두투")
        String channelName,

        @NotNull
        @Schema(description = "완료 개수", example = "2")
        Integer completeCount,

        @NotNull
        @Schema(description = "미완료 개수", example = "2")
        Integer unCompletedCount,

        @NotNull
        @Schema(description = "칭찬했어요 개수", example = "3")
        Integer complimentCount,

        @NotNull
        @Schema(description = "찔렸어요 개수", example = "5")
        Integer pokeCount
) {
    public static ChannelCountStatisticsResponse of(Map<String, Object> statistics) {
        return ChannelCountStatisticsResponse.builder()
                .channelName((String) statistics.get("channelName"))
                .completeCount((Integer) statistics.getOrDefault("completeCount", 0))
                .unCompletedCount((Integer) statistics.getOrDefault("unCompletedCount", 0))
                .complimentCount((Integer) statistics.getOrDefault("complimentCount", 0))
                .pokeCount((Integer) statistics.getOrDefault("pokeCount", 0))
                .build();
    }
}
