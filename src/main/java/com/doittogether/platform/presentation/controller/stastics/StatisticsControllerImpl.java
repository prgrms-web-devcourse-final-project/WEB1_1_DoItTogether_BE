package com.doittogether.platform.presentation.controller.stastics;

import com.doittogether.platform.application.global.code.SuccessCode;
import com.doittogether.platform.application.global.response.SuccessResponse;
import com.doittogether.platform.business.stastics.StatisticsService;
import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.presentation.dto.stastics.ChannelCountStatisticsResponse;
import com.doittogether.platform.presentation.dto.stastics.CompleteScoreResponse;
import com.doittogether.platform.presentation.dto.stastics.MonthlyMVPResponse;
import com.doittogether.platform.presentation.dto.stastics.MonthlyStatisticsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/channels/{channelId}/statistics")
@Tag(name = "통계 API", description = "통계 관련 API")
public class StatisticsControllerImpl implements StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/weekly/{targetDate}/totalCount")
    @Operation(summary = "주간 통계관련 완료 미완료 칭찬 찌르기 개수 조회", description = "주간 통계에서 사용할, 이번주 완료 개수 랭킹을 반환합니다.")
    @Override
    public ResponseEntity<SuccessResponse<ChannelCountStatisticsResponse>> calculateTotalCountByChannelId(
            @AuthenticationPrincipal User user,
            @PathVariable("channelId") Long channelId,
            @RequestParam("targetDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @Parameter(description = "선택 날짜 (yyyy-MM-dd 형식)", example = "2024-11-25") LocalDate targetDate
    ) {
        User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.onSuccess(
                        SuccessCode._OK,
                        statisticsService.calculateTotalCountByChannelId(loginUser, channelId, targetDate)
                ));
    }

    @GetMapping("/weekly/{targetDate}/score")
    @Operation(summary = "주간 통계, 이번주 완료 개수 랭킹", description = "주간통계 중, 이번주 완료 개수 랭킹을 반환합니다.")
    @Override
    public ResponseEntity<SuccessResponse<CompleteScoreResponse>> calculateWeeklyStatistics(
            @AuthenticationPrincipal User user,
            @PathVariable("channelId") Long channelId,
            @RequestParam("targetDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @Parameter(description = "선택 날짜 (yyyy-MM-dd 형식)", example = "2024-11-25") LocalDate targetDate
    ) {
        User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.onSuccess(
                        SuccessCode._OK,
                        statisticsService.calculateWeeklyStatistics(loginUser, channelId, targetDate)
                ));
    }

    @GetMapping("/monthly/{targetMonth}/score")
    @Operation(summary = "월간 통계, 캘린더 부분 조회", description = "월간통계 중, 캘린더 부분에 사용될 데이터를 반환합니다.")
    @Override
    public ResponseEntity<SuccessResponse<MonthlyStatisticsResponse>> calculateMonthlyStatistics(
            @AuthenticationPrincipal User user,
            @PathVariable("channelId") Long channelId,
            @RequestParam("targetMonth")
            @Parameter(description = "선택 월 (yyyy-MM 형식)", example = "2024-11") String targetMonth
    ) {
        User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LocalDate targetDate = LocalDate.parse(targetMonth+"-00");
        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.onSuccess(
                        SuccessCode._OK,
                        statisticsService.calculateMonthlyStatistics(loginUser, channelId, targetDate)
                ));
    }

    @GetMapping("/monthly/{targetMonth}/mvp")
    @Operation(summary = "월간 통계,MVP 부분 조회", description = "월간통계 중, MVP 부분에 사용될 데이터를 반환합니다.")
    @Override
    public ResponseEntity<SuccessResponse<MonthlyMVPResponse>> calculateMonthlyMVP(
            @AuthenticationPrincipal User user,
            @PathVariable("channelId") Long channelId,
            @RequestParam("targetMonth")
            @Parameter(description = "선택 월 (yyyy-MM 형식)", example = "2024-11") String targetMonth
    ) {
        User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LocalDate targetDate = LocalDate.parse(targetMonth+"-00");
        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.onSuccess(
                        SuccessCode._OK,
                        statisticsService.calculateMonthlyMVP(loginUser, channelId, targetDate)
                ));
    }
}
