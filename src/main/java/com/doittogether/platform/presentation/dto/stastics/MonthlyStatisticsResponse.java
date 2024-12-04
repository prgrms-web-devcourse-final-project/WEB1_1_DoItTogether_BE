package com.doittogether.platform.presentation.dto.stastics;

import lombok.Builder;

import java.util.List;

@Builder
public record MonthlyStatisticsResponse(
        List<SingleDayStatisticsResponse> monthlyStatistics
) {

    public static MonthlyStatisticsResponse of (List<SingleDayStatisticsResponse> monthlyStatistics) {
        return MonthlyStatisticsResponse.builder()
                .monthlyStatistics(monthlyStatistics)
                .build();
    }
}
