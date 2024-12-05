package com.doittogether.platform.business.reaction;

import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.domain.enumeration.ReactionType;
import com.doittogether.platform.presentation.dto.reaction.ReactionRequest;

import java.time.LocalDate;
import java.util.Map;

public interface ReactionService {
    void react(User user, Long channelId, ReactionRequest request, ReactionType reactionType);

    Map<String, Integer> calculateReactionStatisticsForWeek(Long channelId, LocalDate targetDate);

    Map<String, Object> calculateReactionsStatisticsMVPForMonthly(Long channelId, LocalDate targetDate);

}
