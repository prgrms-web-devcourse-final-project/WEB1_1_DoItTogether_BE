package com.doittogether.platform.presentation.dto.channel.response;

import com.doittogether.platform.domain.entity.Channel;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "채널의 초대링크 응답")
@Builder
public record ChannelInviteCodeResponse (
        @Schema(description = "채널 아이디")
        Long channelId,

        @Schema(description = "초대 코드")
        String inviteCode
) {
    public static ChannelInviteCodeResponse from(Channel channel, String inviteCode) {
        return ChannelInviteCodeResponse.builder()
                .channelId(channel.getChannelId())
                .inviteCode(inviteCode)
                .build();
    }
}
