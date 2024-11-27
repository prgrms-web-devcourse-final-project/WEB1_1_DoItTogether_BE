package com.doittogether.platform.presentation.dto.channel.response;

import com.doittogether.platform.domain.entity.Channel;
import com.doittogether.platform.domain.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "채널 입장 응답")
@Builder
public record ChannelJoinResponse(
        @Schema(description = "채널 아이디")
        Long channelId,

        @Schema(description = "채널명")
        String name,

        @Schema(description = "사용자가 성공적으로 입장했는지 여부", example = "true")
        boolean joinedSuccessfully
) {
    public static ChannelJoinResponse of(Channel channel, boolean joinedSuccessfully) {
        return ChannelJoinResponse.builder()
                .channelId(channel.getChannelId())
                .name(channel.getName())
                .joinedSuccessfully(joinedSuccessfully)
                .build();
    }
}
