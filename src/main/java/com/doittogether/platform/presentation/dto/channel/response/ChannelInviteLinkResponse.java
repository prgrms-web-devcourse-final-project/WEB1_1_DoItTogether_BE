package com.doittogether.platform.presentation.dto.channel.response;

import com.doittogether.platform.domain.entity.Channel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "채널의 초대링크 응답")
@Builder
public record ChannelInviteLinkResponse(
        @Schema(description = "채널 아이디")
        Long channelId,

        @Schema(description = "초대 링크")
        String inviteLink
) {
    public static ChannelInviteLinkResponse of(Channel channel, String inviteLink) {
        return ChannelInviteLinkResponse.builder()
                .channelId(channel.getChannelId())
                .inviteLink(inviteLink)
                .build();
    }
}
