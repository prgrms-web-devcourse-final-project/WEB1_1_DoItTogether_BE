package com.doittogether.platform.presentation.dto.channel.response;

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
    public static ChannelJoinResponse of(Long channelId, String name, boolean joinedSuccessfully) {
        return ChannelJoinResponse.builder()
                .channelId(channelId)
                .name(name)
                .joinedSuccessfully(joinedSuccessfully)
                .build();
    }
}
