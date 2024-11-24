package com.doittogether.platform.presentation.dto.channel.response;

import com.doittogether.platform.domain.entity.Channel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "채널의 집안일 통계 응답")
@Builder
public record ChannelStatisticsResponse (
        @Schema(description = "채널 아이디")
        Long channelId,

        @Schema(description = "집안일 전체 완료 수")
        int totalCompleted,

        @Schema(description = "집안일 전체 미완료 수")
        int totalIncomplete
){
    public static ChannelStatisticsResponse of(Channel channel, int totalCompleted, int totalIncomplete) {
        return ChannelStatisticsResponse.builder()
                .channelId(channel.getChannelId())
                .totalCompleted(totalCompleted)
                .totalIncomplete(totalIncomplete)
                .build();
    }
}
