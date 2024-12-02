package com.doittogether.platform.presentation.dto.stastics;

import java.util.List;

public record MonthlyStatisticsResponse(
        List<SingleDayStatisticsResponse> monthlyStatistics
) {
}
