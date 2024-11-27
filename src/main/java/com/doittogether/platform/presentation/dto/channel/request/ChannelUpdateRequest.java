package com.doittogether.platform.presentation.dto.channel.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "채널 수정 요청")
public record ChannelUpdateRequest (
        @NotBlank(message = "채널명은 필수 입력 값입니다.")
        @Schema(description = "채널명")
        String name
) {
}
