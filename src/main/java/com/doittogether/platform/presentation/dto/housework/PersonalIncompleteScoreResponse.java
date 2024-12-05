package com.doittogether.platform.presentation.dto.housework;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PersonalIncompleteScoreResponse (

        @NotNull
        @Schema(description = "일간 날짜", example = "2024-12-05")
        LocalDate date,

        @NotNull
        @Schema(description = "집안일 미 완료 개수", example = "10")
        Integer houseworkIncompleteCount,

        @NotNull
        @Schema(description = "모든 집안일 해결 여부", example = "true")
        boolean solvedMatters
){
    public LocalDate retrieveDate(){
        return date;
    }
}
