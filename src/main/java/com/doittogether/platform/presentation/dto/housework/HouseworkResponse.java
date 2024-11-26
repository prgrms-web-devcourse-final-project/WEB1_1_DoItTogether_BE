package com.doittogether.platform.presentation.dto.housework;

import static lombok.AccessLevel.PRIVATE;

import com.doittogether.platform.domain.entity.Housework;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder(access = PRIVATE)
@Schema(description = "작업 리스트와 담당자 정보 응답")
public record HouseworkResponse(
        @NotBlank
        @Schema(description = "카테고리 이름", example = "거실")
        String category,

        @NotNull
        @Schema(description = "카테고리에 해당하는 작업 목록", example = "먼지 닦기")
        String task,

        @NotBlank
        @Schema(description = "진행 날짜", example = "2024-11-23 00:00:00")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        LocalDateTime startDateTime,

        @NotBlank
        @Schema(description = "하루 종일 여부", example = "true")
        Boolean isAllDay,

        @NotBlank
        @Schema(description = "작업 담당자 이름", example = "홍길동")
        String assignee
) {
    public static HouseworkResponse from(Housework housework) {
        return HouseworkResponse.builder()
                .category(housework.getCategory().getDisplayName())
                .task(housework.getTask())
                .assignee(housework.getAssignee().getNickName())
                .build();
    }
}