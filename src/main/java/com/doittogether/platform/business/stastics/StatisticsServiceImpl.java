package com.doittogether.platform.business.stastics;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.exception.statistics.StatisticsException;
import com.doittogether.platform.business.channel.ChannelValidator;
import com.doittogether.platform.domain.entity.Assignee;
import com.doittogether.platform.domain.entity.Housework;
import com.doittogether.platform.domain.entity.Status;
import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.domain.enumeration.CompletionStatus;
import com.doittogether.platform.infrastructure.persistence.UserRepository;
import com.doittogether.platform.infrastructure.persistence.housework.HouseworkRepository;
import com.doittogether.platform.presentation.dto.stastics.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final HouseworkRepository houseworkRepository;
    private final UserRepository userRepository;
    private final ChannelValidator channelValidator;

    @Override
    public CompleteScoreResponse calculateWeeklyStatistics(User loginUser, Long channelId, LocalDate targetDate) {

        channelValidator.validateExistChannel(channelId);

        LocalDate startOfWeek;
        LocalDate endOfWeek;

        if (targetDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            // targetDate가 일요일이면
            startOfWeek = targetDate; // 그대로 반환
            endOfWeek = targetDate.with(TemporalAdjusters.next(DayOfWeek.SATURDAY)); // 다음 주 토요일
        } else {
            // targetDate가 다른 요일이면
            startOfWeek = targetDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)); // 지난주 일요일
            endOfWeek = targetDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY)); // 이번 주 토요일
        }

        final List<Housework> houseworkList = houseworkRepository.findByChannelChannelIdAndStartDateBetween(channelId, startOfWeek, endOfWeek);
        try {
            final List<PersonalCompleteScoreResponse> statisticsList = generateWeeklyStatistics(houseworkList);
            return CompleteScoreResponse.of(statisticsList);
        } catch (IllegalArgumentException e) {
            throw new StatisticsException(ExceptionCode.HOUSEWORK_NOT_NULL);
        }
    }

    @Override
    public ChannelCountStatisticsResponse calculateTotalCountByChannelId(User loginUser, Long channelId,
                                                                         LocalDate targetDate) {
        return null;
    }

    @Override
    public MonthlyStatisticsResponse calculateMonthlyStatistics(User loginUser, Long channelId, LocalDate targetDate) {

        channelValidator.validateExistChannel(channelId);

        LocalDate firstDayOfMonth = targetDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDayOfMonth = targetDate.with(TemporalAdjusters.lastDayOfMonth());

        List<Housework> houseworkList = houseworkRepository.findByChannelChannelIdAndStartDateBetween(channelId, firstDayOfMonth, lastDayOfMonth);
        try {
            List<SingleDayStatisticsResponse> statisticsList = generateMonthlyStatistics(houseworkList);
            return MonthlyStatisticsResponse.of(statisticsList);
        } catch (IllegalArgumentException e) {
            throw new StatisticsException(ExceptionCode.HOUSEWORK_NOT_NULL);
        }
    }

    @Override
    public MonthlyMVPResponse calculateMonthlyMVP(User loginUser, Long channelId, LocalDate targetDate) {
        return null;
    }

    // 아래 메서드들 Util로 빼야 하는가?
    public List<PersonalCompleteScoreResponse> generateWeeklyStatistics(List<Housework> houseworkList) {

        // 담당자 별로 집안일을 그룹화
        Map<Assignee, List<Housework>> groupedByNickName = houseworkList.stream()
                .collect(Collectors.groupingBy(Housework::retrieveAssignee));

        //그룹회된 담당자 별로 통계 계산
        List<PersonalCompleteScoreResponse> statisticsList = new ArrayList<>();

        for (Map.Entry<Assignee, List<Housework>> entry : groupedByNickName.entrySet()) {
            Assignee assignee = entry.getKey();
            List<Housework> dailyHouseworks = entry.getValue();

            // Response 필드 정보 조회
            String nickName = assignee.retrieveUser().retrieveNickName();
            long completedTasks = dailyHouseworks.stream()
                    .filter(housework -> housework.retrieveStatus() == Status.COMPLETE)
                    .count();
            String url = userRepository.findProfileImageUrlByNickName(nickName).orElse("");

            // 사용자 별 응답 객체 생성
            statisticsList.add(new PersonalCompleteScoreResponse(nickName, Math.toIntExact(completedTasks), url));
        }

        // 집안일 완료 개수 내림차 순으로 정렬
        statisticsList.sort(Comparator.comparing(PersonalCompleteScoreResponse::completeCount).reversed());

        return statisticsList;
    }

    public List<SingleDayStatisticsResponse> generateMonthlyStatistics(List<Housework> houseworkList) {
        // 날짜별로 집안일을 그룹화
        Map<LocalDate, List<Housework>> groupedByDate = houseworkList.stream()
                .collect(Collectors.groupingBy(Housework::retrieveStartDate));

        // 그룹화된 날짜별로 통계 계산
        List<SingleDayStatisticsResponse> statisticsList = new ArrayList<>();

        for (Map.Entry<LocalDate, List<Housework>> entry : groupedByDate.entrySet()) {
            LocalDate date = entry.getKey();
            List<Housework> dailyHouseworks = entry.getValue();

            // 전체 집안일 개수
            int totalTasks = dailyHouseworks.size();

            // 완료된 집안일 개수
            long completedTasks = dailyHouseworks.stream()
                    .filter(housework -> housework.retrieveStatus() == Status.COMPLETE)
                    .count();

            // 완료 상태 (3가지 중 하나: ALL_DONE, INCOMPLETE_REMAINING, NO_HOUSEWORK)
            CompletionStatus status = calculateCompletionStatus(dailyHouseworks, totalTasks);

            // 결과 리스트에 추가
            statisticsList.add(new SingleDayStatisticsResponse(date, totalTasks, (int) completedTasks, status));
        }

        // 날짜 기준으로 오름차순 정렬
        statisticsList.sort(Comparator.comparing(SingleDayStatisticsResponse::retrieveDate));
        return statisticsList;
    }

    private CompletionStatus calculateCompletionStatus(List<Housework> houseworks, int totalTasks) {
        if (totalTasks == 0) {
            return CompletionStatus.NO_HOUSEWORK;
        }
        boolean allComplete = houseworks.stream().allMatch(h -> h.retrieveStatus() == Status.COMPLETE);
        if (allComplete) {
            return CompletionStatus.ALL_DONE;
        } else {
            return CompletionStatus.INCOMPLETE_REMAINING;
        }
    }
}
