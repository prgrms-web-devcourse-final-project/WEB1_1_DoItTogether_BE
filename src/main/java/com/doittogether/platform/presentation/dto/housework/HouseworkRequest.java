package com.doittogether.platform.presentation.dto.housework;

import com.doittogether.platform.domain.entity.HouseworkCategory;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Schema(description = "집안일 수정 요청 DTO")
public record HouseworkRequest(
        @NotBlank(message = "카테고리 이름을 입력해주세요.")
        @Schema(description = "카테고리 이름", example = "LIVING_ROOM")
        HouseworkCategory category,

        @NotBlank(message = "작업 이름을 입력해주세요.")
        @Schema(description = "작업 이름", example = "먼지 닦기")
        String task,

        @NotBlank(message = "시작 날짜와 시간을 입력해주세요. 패턴은 yyyy-MM-dd HH:mm과 같습니다.")
        @Schema(description = "시작 날짜와 시간", example = "2021-11-30 00:00:00", type = "string")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime startDateTime,

        @NotBlank(message = "하루 종일 여부를 입력해주세요.")
        @Schema(description = "하루 종일 여부", example = "true")
        Boolean isAllDay,

        @NotBlank(message = "담당자의 유저ID를 입력해주세요.")
        @Schema(description = "작업 담당자 ID", example = "1")
        Long userId
) {
    public static HouseworkRequest of(HouseworkCategory category, String task, LocalDateTime startDateTime, Boolean isAllDay,
                               Long userId) {
        return new HouseworkRequest(category, task, startDateTime, isAllDay, userId);
    }
}
