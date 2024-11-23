package com.doittogether.platform.presentation.dto.channel.response;

import com.doittogether.platform.domain.entity.Channel;
import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.domain.entity.UserChannel;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "채널의 유저 목록 응답")
@Builder
public record ChannelUserListResponse(
        @Schema(description = "채널 아이디")
        Long channelId,

        @Schema(description = "채널 내의 모든 회원 리스트")
        List<User> userList
) {
    public static ChannelUserListResponse from(Channel channel) {
        return ChannelUserListResponse.builder()
                .userList(channel.getUserChannels().stream()
                        .map(UserChannel::getUser)
                        .toList())
                .build();
    }
}
