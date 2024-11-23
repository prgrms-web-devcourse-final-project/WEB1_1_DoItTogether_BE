package com.doittogether.platform.presentation.dto.channel.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "채널의 수정 응답")
public record ChannelUpdateResponse (
        @Schema(description = "채널 아이디")
        Long channelId
) {
    public static ChannelUpdateResponse from(Long channelId) {
        return new ChannelUpdateResponse(channelId);
    }
}
