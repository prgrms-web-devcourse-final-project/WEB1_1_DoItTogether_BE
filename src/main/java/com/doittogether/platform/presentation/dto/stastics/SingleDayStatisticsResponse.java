package com.doittogether.platform.presentation.dto.stastics;

import com.doittogether.platform.domain.enumeration.CompletionStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record SingleDayStatisticsResponse(

        @NotBlank
        @Schema(description = "날짜", example = "2024-11-01")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate date,

        @NotNull
        @Schema(description = "등록된 전체 집안일 개수", example = "3")
        Integer totalTasks,

        @NotNull
        @Schema(description = "완료 개수", example = "2")
        Integer completedTasks,

        @NotNull
        @Schema(description = "완료 상태 ALL_DONE,INCOMPLETE_REMAINING, NO_HOUSEWORK", example = "INCOMPLETE_REMAINING")
        CompletionStatus status

) {

        public LocalDate retrieveDate(){
                return date;
        }
}
