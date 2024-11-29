package com.doittogether.platform.presentation.dto.channel.response;

import com.doittogether.platform.domain.entity.Channel;
import com.doittogether.platform.domain.entity.UserChannel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "채널 목록 응답")
@Builder
public record ChannelResponse (
        @Schema(description = "채널 아이디")
        Long channelId,

        @Schema(description = "채널 이름")
        String name
) {
    public static ChannelResponse from(UserChannel userChannel) {
        return ChannelResponse.builder()
                .channelId(userChannel.getChannel().getChannelId())
                .name(userChannel.getChannel().getName())
                .build();
    }
}
