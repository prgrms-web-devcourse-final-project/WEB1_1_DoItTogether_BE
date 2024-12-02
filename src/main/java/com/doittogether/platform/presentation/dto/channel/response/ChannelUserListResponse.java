package com.doittogether.platform.presentation.dto.channel.response;

import com.doittogether.platform.domain.entity.Channel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

@Schema(description = "채널의 유저 목록 응답")
@Builder
public record ChannelUserListResponse(
        @Schema(description = "채널 아이디")
        Long channelId,

        @Schema(description = "채널명")
        String name,

        @Schema(description = "채널 내의 모든 회원 리스트")
        List<UserChannelResponse> userList,

        @Schema(description = "총 사용자 수")
        long totalElements,

        @Schema(description = "총 페이지 수")
        int totalPages
) {
    public static ChannelUserListResponse of(Channel channel, Page<UserChannelResponse> userChannelPage) {
        return ChannelUserListResponse.builder()
                .channelId(channel.getChannelId())
                .name(channel.getName())
                .userList(userChannelPage.getContent())
                .totalElements(userChannelPage.getTotalElements())
                .totalPages(userChannelPage.getTotalPages())
                .build();
    }

}
