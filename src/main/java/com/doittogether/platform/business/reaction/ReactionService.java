package com.doittogether.platform.business.reaction;

import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.domain.enumeration.ReactionType;

import java.time.LocalDate;
import java.util.Map;

public interface ReactionService {
    void react(User user, Long targetUserId, Long channelId, ReactionType reactionType);

    Map<String, Integer> calculateReactionStatisticsForWeek(Long channelId, LocalDate targetDate);

    Map<String, Object> calculateReactionsStatisticsMVPForMonthly(Long channelId, LocalDate targetDate);

}
