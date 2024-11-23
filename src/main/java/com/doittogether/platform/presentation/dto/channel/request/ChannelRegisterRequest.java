package com.doittogether.platform.presentation.dto.channel.request;

import com.doittogether.platform.domain.entity.Channel;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "채널 등록 요청")
public record ChannelRegisterRequest(
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
