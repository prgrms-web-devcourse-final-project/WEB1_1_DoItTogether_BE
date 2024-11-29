package com.doittogether.platform.presentation.dto.channel.response;

import com.doittogether.platform.domain.entity.UserChannel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "채널 사용자 정보 응답")
@Builder
public record UserChannelResponse(
        @Schema(description = "사용자 아이디") Long userId,
        @Schema(description = "사용자 닉네임") String nickName,
        @Schema(description = "사용자 이메일") String email,
        @Schema(description = "사용자 역할") String role
) {
    public static UserChannelResponse from(UserChannel userChannel) {
        return UserChannelResponse.builder()
                .userId(userChannel.getUser().getUserId())
                .nickName(userChannel.getUser().getNickName())
                .email(userChannel.getUser().getEmail())
                .role(userChannel.getRole().name())
                .build();
    }
}