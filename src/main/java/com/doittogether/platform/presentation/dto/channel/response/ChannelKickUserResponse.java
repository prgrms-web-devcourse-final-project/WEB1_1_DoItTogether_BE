package com.doittogether.platform.presentation.dto.channel.response;

import com.doittogether.platform.domain.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "채널 유저 추방 응답")
@Builder
public record ChannelKickUserResponse(
        @Schema(description = "추방된 유저 이메일")
        String email,

        @Schema(description = "추방된 유저 닉네임")
        String nickName
) {
    public static ChannelKickUserResponse from(User user) {
        return ChannelKickUserResponse.builder()
                .email(user.retrieveEmail())
                .nickName(user.retrieveNickName())
                .build();
    }
}
