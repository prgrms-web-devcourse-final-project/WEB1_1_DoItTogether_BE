package com.doittogether.platform.business.stastics;

import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.presentation.dto.stastics.ChannelCountStatisticsResponse;
import com.doittogether.platform.presentation.dto.stastics.CompleteScoreResponse;
import java.time.LocalDate;

public interface StatisticsService {
    CompleteScoreResponse calculateWeeklyStatistics(User loginUser, Long channelId, LocalDate targetDate);

    ChannelCountStatisticsResponse calculateTotalCountByChannelId(User loginUser, Long channelId, LocalDate targetDate);
}
