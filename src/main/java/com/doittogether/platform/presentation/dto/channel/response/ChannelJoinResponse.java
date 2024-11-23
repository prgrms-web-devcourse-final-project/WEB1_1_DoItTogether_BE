package com.doittogether.platform.presentation.dto.channel.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 채널 입장에 대한 응답
 *
 * @author ycjung
 */
@Schema(description = "채널 입장 응답")
public record ChannelJoinResponse(
        @Schema(description = "채널 아이디")
        Long channelId,

        @Schema(description = "채널명")
        String name,

        @Schema(description = "사용자가 성공적으로 입장했는지 여부", example = "true")
        boolean joinedSuccessfully
) {
    public static ChannelJoinResponse from(Long channelId, String name, boolean joinedSuccessfully) {
        return new ChannelJoinResponse(channelId, name, joinedSuccessfully);
    }
}
