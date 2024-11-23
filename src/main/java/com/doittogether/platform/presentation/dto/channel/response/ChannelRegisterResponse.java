package com.doittogether.platform.presentation.dto.channel.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "채널의 생성 응답")
@Builder
public record ChannelRegisterResponse (
        @Schema(description = "채널 아이디")
        Long channelId,

        @Schema(description = "채널명")
        String name
) {
    public static ChannelRegisterResponse of(Long channelId, String name) {
        return ChannelRegisterResponse.builder()
                .channelId(channelId)
                .name(name)
                .build();
    }
}
