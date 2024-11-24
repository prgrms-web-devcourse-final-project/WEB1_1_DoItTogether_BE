package com.doittogether.platform.presentation.dto.channel.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "채널 유저 추방 응답")
@Builder
public record ChannelKickUserResponse (
        @Schema(description = "추방된 유저 이메일")
        String email
) {
    ChannelKickUserResponse from(String email) {
        return ChannelKickUserResponse.builder()
                .email(email)
                .build();
    }
}
