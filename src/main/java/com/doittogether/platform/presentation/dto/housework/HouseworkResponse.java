package com.doittogether.platform.presentation.dto.housework;

import static lombok.AccessLevel.PRIVATE;

import com.doittogether.platform.domain.entity.Housework;
import com.doittogether.platform.domain.entity.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;

@Builder(access = PRIVATE)
@Schema(description = "작업 리스트와 담당자 정보 응답")
public record HouseworkResponse(
        @NotBlank
        @Schema(description = "집안일 ID", example = "1")
        Long houseworkId,

        @NotBlank
        @Schema(description = "카테고리 이름", example = "거실")
        String category,

        @NotNull
        @Schema(description = "카테고리에 해당하는 작업 목록", example = "먼지 닦기")
        String task,

        @NotBlank
        @Schema(description = "진행 날짜", example = "2024-11-23")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate startDate,

        @Nullable
        @Schema(description = "진행 시간", example = "OO:OO")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
        LocalTime startTime,

        @NotBlank
        @Schema(description = "하루 종일 여부", example = "false")
        Boolean isAllDay,

        @NotBlank
        @Schema(description = "작업 담당자 이름", example = "홍길동")
        String assignee,

        @NotNull
        @Schema(description = "작업 담당자 userId", example = "홍길동")
        Long userId,

        @NotNull
        @Schema(description = "집안일 완료여부_ COMPLETE 또는 NOT_COMPLETE로 구분", example = "COMPLETE")
        Status status,

        @NotNull
        @Schema(description = "작업 담당자 assigneeId", example = "홍길동")
        Long assigneeId
) {
    public static HouseworkResponse from(Housework housework) {
        return HouseworkResponse.builder()
                .houseworkId(housework.retrieveHouseworkId())
                .category(housework.retrieveCategory().getDisplayName())
                .task(housework.retrieveTask())
                .startDate(housework.retrieveStartDate())
                .startTime(housework.retrieveStartTime())
                .isAllDay(housework.isAllDay())
                .userId(housework.retrieveAssignee().retrieveUser().retrieveUserId())
                .status(housework.retrieveStatus())
                .assigneeId(housework.retrieveAssignee().retrieveAssigneeId())
                .assignee(housework.retrieveAssignee().retrieveUser().retrieveNickName())
                .build();
    }
}