package com.doittogether.platform.business.stastics;

import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.presentation.dto.stastics.CompleteScoreResponse;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImpl implements StatisticsService{
    @Override
    public CompleteScoreResponse calculateWeeklyStatistics(User loginUser, Long channelId, LocalDate targetDate) {
        return null;
    }
}
