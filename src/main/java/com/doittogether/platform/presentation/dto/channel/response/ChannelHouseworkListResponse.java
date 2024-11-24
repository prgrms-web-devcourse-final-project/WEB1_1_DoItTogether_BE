package com.doittogether.platform.presentation.dto.channel.response;

import com.doittogether.platform.domain.entity.Channel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Schema(description = "집안일 목록 조회 응답")
@Builder
public record ChannelHouseworkListResponse(
        @Schema(description = "채널 아이디")
        Long channelId,

        @Schema(description = "채널 내의 집안일 리스트")
        List<HouseworkResponse> houseworkList
) {
    public static ChannelHouseworkListResponse from(Channel channel) {
        return ChannelHouseworkListResponse.builder()
                .channelId(channel.getChannelId())
                .houseworkList(channel.getHouseworks().stream()
                        .map(HouseworkResponse::from)
                        .toList())
                .build();
    }
}
