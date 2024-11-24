package com.doittogether.platform.presentation.dto.channel.response;

import com.doittogether.platform.domain.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record UserResponse(
        @Schema(description = "유저 아이디")
        Long userId,

        @Schema(description = "이메일")
        String email,

        @Schema(description = "닉네임")
        String nickName
) {
    public static UserResponse from(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .nickName(user.getNickName())
                .build();
    }
}
