package com.doittogether.platform.business.reaction;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.exception.reaction.ReactionException;
import com.doittogether.platform.domain.entity.Channel;
import com.doittogether.platform.domain.entity.Reaction;
import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.domain.enumeration.ReactionType;
import com.doittogether.platform.infrastructure.persistence.channel.ChannelRepository;
import com.doittogether.platform.infrastructure.persistence.reaction.ReactionRepository;
import com.doittogether.platform.infrastructure.persistence.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {

    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final ReactionRepository reactionRepository;

    @Override
    public void react(User user, Long targetUserId, Long channelId, ReactionType reactionType) {
        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new ReactionException(ExceptionCode.TARGET_USER_NOT_FOUND));

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ReactionException(ExceptionCode.CHANNEL_NOT_FOUND));

        Reaction reaction = Reaction.of(
                user,
                targetUser,
                channel,
                reactionType
        );

        reactionRepository.save(reaction);
    }

    @Override
    public Map<String, Integer> calculateReactionStatisticsForWeek(Long channelId, LocalDate targetDate) {
        LocalDate startOfWeek = targetDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)); // 일 부터
        LocalDate endOfWeek = targetDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY)); // 토 까지

        int complimentCount = reactionRepository.countReactionsByChannelAndTypeAndDateRange(
                channelId, ReactionType.COMPLIMENT, startOfWeek.atStartOfDay(), endOfWeek.atStartOfDay());

        int pokeCount = reactionRepository.countReactionsByChannelAndTypeAndDateRange(
                channelId, ReactionType.POKE, startOfWeek.atStartOfDay(), endOfWeek.atStartOfDay());

        Map<String, Integer> statistics = new HashMap<>();
        statistics.put("complimentCount", complimentCount);
        statistics.put("pokeCount", pokeCount);
        return statistics;
    }

    @Override
    public Map<String, Object> calculateReactionsStatisticsMVPForMonthly(Long channelId, LocalDate targetDate) {
        Map<String, Object> statistics = new HashMap<>();
        LocalDateTime startDate = targetDate.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endDate = targetDate.withDayOfMonth(1).plusMonths(1).atStartOfDay();
        PageRequest pageRequest = PageRequest.of(0, 1);

        List<Object[]> complimentResults = reactionRepository.findTopUserByReactionType(
                channelId, startDate, endDate, ReactionType.COMPLIMENT, pageRequest
        );

        if(!complimentResults.isEmpty()) {
            Object[] complimentTopResult = complimentResults.get(0);
            statistics.put("complimentMVPUserId", (Long) complimentTopResult[0]);
            statistics.put("complimentMVPNickName", (String) complimentTopResult[1]);
            statistics.put("complimentMVPCount", (Long) complimentTopResult[2]);
        }

        List<Object[]> pokeResults  = reactionRepository.findTopUserByReactionType(
                channelId, startDate, endDate, ReactionType.POKE, pageRequest
        );

        if(!pokeResults.isEmpty()) {
            Object[] pokeTopResult = pokeResults.get(0);
            statistics.put("pokeMVPUserId", (Long) pokeTopResult[0]);
            statistics.put("pokeMVPNickName", (String) pokeTopResult[1]);
            statistics.put("pokeMVPCount", (Long) pokeTopResult[2]);
        }

        return statistics;
    }
}
