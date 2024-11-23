package com.doittogether.platform.presentation.dto.channel.response;

import com.doittogether.platform.domain.entity.Channel;
import com.doittogether.platform.domain.entity.Housework;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "채널의 집안일 리스트 응답")
@Builder
public record ChannelHouseworkListResponse (
        @Schema(description = "채널 아이디")
        Long channelId,

        @Schema(description = "채널 내의 집안일 리스트")
        List<Housework> houseworkList
) {
    public static ChannelHouseworkListResponse from(Channel channel) {
        return ChannelHouseworkListResponse.builder()
                .channelId(channel.getChannelId())
                .houseworkList(channel.getHouseworks())
                .build();
    }
}
