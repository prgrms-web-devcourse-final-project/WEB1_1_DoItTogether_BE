package com.doittogether.platform.presentation.dto.channel.response;

import com.doittogether.platform.domain.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

@Schema(description = "내가 속한 채널 목록 응답")
@Builder
public record ChannelListResponse (
        @Schema(description = "유저 아이디")
        Long userId,

        @Schema(description = "내가 속한 채널 리스트")
        List<ChannelResponse> channelList
) {
    public static ChannelListResponse of(User user, Page<ChannelResponse> channelResponsePage) {
        return ChannelListResponse.builder()
                .userId(user.retrieveUserId())
                .channelList(channelResponsePage.getContent())
                .build();
    }
}
