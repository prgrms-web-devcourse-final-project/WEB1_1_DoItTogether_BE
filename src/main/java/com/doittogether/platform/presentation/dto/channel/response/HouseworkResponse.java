package com.doittogether.platform.presentation.dto.channel.response;

import com.doittogether.platform.domain.entity.Housework;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Schema(description = "집안일 응답")
@Builder
public record HouseworkResponse(
        @Schema(description = "집안일 아이디")
        Long houseworkId,

        @Schema(description = "집안일 시작 시간")
        LocalDateTime startDateTime,

        @Schema(description = "집안일 상태")
        String status,

        @Schema(description = "집안일 배정 사용자 아이디")
        Long userId
) {
    public static HouseworkResponse from(Housework housework) {
        return HouseworkResponse.builder()
                .houseworkId(housework.getHouseworkId())
                .startDateTime(housework.getStartDateTime())
                .status(housework.getStatus().name())
                .userId(housework.getUser().getUserId())
                .build();
    }
}
