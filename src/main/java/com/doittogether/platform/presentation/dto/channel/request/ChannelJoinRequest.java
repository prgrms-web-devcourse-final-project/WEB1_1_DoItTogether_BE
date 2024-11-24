package com.doittogether.platform.presentation.dto.channel.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "채널 입장 요청")
public record ChannelJoinRequest(
        @NotBlank(message = "초대 코드는 필수 입력 값입니다.")
        @Schema(description = "초대 코드")
        String inviteCode
) {
}
