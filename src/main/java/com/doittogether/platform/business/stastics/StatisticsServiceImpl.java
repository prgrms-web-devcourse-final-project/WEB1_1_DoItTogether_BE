package com.doittogether.platform.business.stastics;

import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.presentation.dto.stastics.ChannelCountStatisticsResponse;
import com.doittogether.platform.presentation.dto.stastics.CompleteScoreResponse;
import com.doittogether.platform.presentation.dto.stastics.MonthlyMVPResponse;
import com.doittogether.platform.presentation.dto.stastics.MonthlyStatisticsResponse;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImpl implements StatisticsService{
    @Override
    public CompleteScoreResponse calculateWeeklyStatistics(User loginUser, Long channelId, LocalDate targetDate) {
        return null;
    }

    @Override
    public ChannelCountStatisticsResponse calculateTotalCountByChannelId(User loginUser, Long channelId,
                                                    LocalDate targetDate) {
        return null;
    }

    @Override
    public MonthlyStatisticsResponse calculateMonthlyStatistics(User loginUser, Long channelId, LocalDate targetDate) {
        return null;
    }

    @Override
    public MonthlyMVPResponse calculateMonthlyMVP(User loginUser, Long channelId, LocalDate targetDate) {
        return null;
    }
}
