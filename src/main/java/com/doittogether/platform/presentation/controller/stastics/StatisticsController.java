package com.doittogether.platform.presentation.controller.stastics;

import com.doittogether.platform.application.global.response.SuccessResponse;
import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.presentation.dto.stastics.CompleteScoreResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface StatisticsController {
    @GetMapping("/{targetDate}/{pageNumber}/{pageSize}")
    @Operation(summary = "주간 통계, 이번주 완료 개수 랭킹", description = "주간통계 중, 이번주 완료 개수 랭킹을 반환합니다.")
    ResponseEntity<SuccessResponse<CompleteScoreResponse>> calculateWeeklyStatistics(
            @AuthenticationPrincipal User user,
            @PathVariable("channelId") Long channelId,
            @RequestParam("targetDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @Parameter(description = "선택 날짜 (yyyy-MM-dd 형식)", example = "2024-11-25") LocalDate targetDate
    );
}
