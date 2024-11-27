package com.doittogether.platform.presentation.dto.channel.request;

import com.doittogether.platform.domain.entity.Channel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "채널 등록 요청")
public record ChannelRegisterRequest(
        @NotBlank(message = "채널명은 필수 입력 값입니다.")
        @Schema(description = "채널명")
        String name
) {
    public static ChannelRegisterRequest of(String name) {
        return new ChannelRegisterRequest(name);
    }

    public static Channel toEntity(ChannelRegisterRequest channelRegisterRequest) {
        return Channel.builder()
                .name(channelRegisterRequest.name)
                .build();
    }
}
