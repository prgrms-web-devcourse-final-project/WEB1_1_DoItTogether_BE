package com.doittogether.platform.presentation.dto.user.response;

import com.doittogether.platform.domain.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "사용자 프로필 업데이트 응답")
@Builder
public record UserUpdateResponse(
        @Schema(description = "사용자 ID")
        Long userId
) {
    public static UserUpdateResponse from(User user) {
        return UserUpdateResponse.builder()
                .userId(user.retrieveUserId())
                .build();
    }
}