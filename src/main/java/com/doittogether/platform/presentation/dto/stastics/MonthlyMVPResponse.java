package com.doittogether.platform.presentation.dto.stastics;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Map;

@Builder
public record MonthlyMVPResponse(

        @NotNull
        @Schema(description = "칭찬 MVP 회원 아이디", example = "1")
        Long complimentMVPUserId,

        @NotNull
        @Schema(description = "칭찬 MVP", example = "이서준")
        String complimentMVPNickName,

        @NotNull
        @Schema(description = "칭찬 MVP 가 받은 칭찬 갯수")
        Long complimentMVPCount,

        @NotNull
        @Schema(description = "찌르기 MVP 회원 아이디", example = "이서준")
        Long pokeMVPUserId,

        @NotNull
        @Schema(description = "찌르기 MVP", example = "이서준")
        String pokeMVPNickName,

        @NotNull
        @Schema(description = "찌르기 MVP 가 받은 찔림 갯수")
        Long pokeMVPCount
) {
        public static MonthlyMVPResponse of(Map<String, Object> statistics) {
                return MonthlyMVPResponse.builder()
                        .complimentMVPUserId((Long) statistics.get("complimentMVPUserId"))
                        .complimentMVPNickName((String) statistics.get("complimentMVPNickName"))
                        .complimentMVPCount(((Long) statistics.get("complimentMVPCount")))
                        .pokeMVPUserId((Long) statistics.get("pokeMVPUserId"))
                        .pokeMVPNickName((String) statistics.get("pokeMVPNickName"))
                        .pokeMVPCount(((Long) statistics.get("pokeMVPCount")))
                        .build();
        }
}
