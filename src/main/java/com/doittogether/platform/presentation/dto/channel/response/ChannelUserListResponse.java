package com.doittogether.platform.presentation.dto.channel.response;

import com.doittogether.platform.domain.entity.Channel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Schema(description = "채널의 유저 목록 응답")
@Builder
public record ChannelUserListResponse(
        @Schema(description = "채널 아이디")
        Long channelId,

        @Schema(description = "채널 내의 모든 회원 리스트")
        List<UserResponse> userList
) {
    public static ChannelUserListResponse of(Channel channel, List<UserResponse> userList) {
        return ChannelUserListResponse.builder()
                .channelId(channel.getChannelId())
                .userList(userList)
                .build();
    }
}
