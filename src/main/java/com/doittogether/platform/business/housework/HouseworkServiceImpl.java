package com.doittogether.platform.business.housework;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.exception.housework.HouseworkException;
import com.doittogether.platform.business.channel.ChannelValidator;
import com.doittogether.platform.domain.entity.*;
import com.doittogether.platform.domain.enumeration.HouseworkCategory;
import com.doittogether.platform.domain.enumeration.Status;
import com.doittogether.platform.infrastructure.persistence.user.UserRepository;
import com.doittogether.platform.infrastructure.persistence.housework.AssigneeRepository;
import com.doittogether.platform.infrastructure.persistence.housework.HouseworkRepository;
import com.doittogether.platform.presentation.dto.housework.HouseworkRequest;
import com.doittogether.platform.presentation.dto.housework.HouseworkResponse;
import com.doittogether.platform.presentation.dto.housework.HouseworkSliceResponse;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class HouseworkServiceImpl implements HouseworkService {

    private final EntityManager entityManager;
    private final HouseworkRepository houseworkRepository;
    private final AssigneeRepository assigneeRepository;
    private final HouseworkValidator houseworkValidator;
    private final ChannelValidator channelValidator;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public HouseworkSliceResponse findAllByChannelIdAndTargetDate(final User loginUser, final Long channelId, final
    LocalDate targetDate, final Pageable pageable) {
        channelValidator.validateExistChannel(channelId);
        final Slice<Housework> houseworks = houseworkRepository.findAllByChannelIdAndTargetDate(
                channelId, loginUser.retrieveUserId(), pageable, targetDate);

        return HouseworkSliceResponse.from(houseworks);
    }

    @Override
    @Transactional(readOnly = true)
    public HouseworkSliceResponse findAllByChannelIdAndTargetDateAndAssigneeId(final Long channelId, final
    LocalDate targetDate,
                                                                               final Long assigneeId,
                                                                               final Pageable pageable) {
        channelValidator.validateExistChannel(channelId);
        final Slice<Housework> houseworks = houseworkRepository.findAllByChannelIdAndTargetDateAndAssigneeId(channelId,
                assigneeId, pageable, targetDate);
        return HouseworkSliceResponse.from(houseworks);
    }

    @Override
    public HouseworkResponse findHouseworkByChannelIdAndHouseworkId(final User loginUser,
                                                                    final Long channelId,
                                                                    final Long houseworkId) {
        channelValidator.validateExistChannel(channelId);
        houseworkValidator.validateExistHousework(houseworkId);
        // 같은 채널에 있는 지 확인 로직 추가 필요
        Housework housework = houseworkRepository.findByChannelChannelIdAndHouseworkId(channelId, houseworkId)
                .orElseThrow(() -> new HouseworkException(ExceptionCode.HOUSEWORK_NOT_FOUND));

        return HouseworkResponse.from(housework);
    }

    @Override
    public void addHousework(final Long channelId, final HouseworkRequest request) {
        channelValidator.validateExistChannel(channelId);
        final Channel channel = entityManager.getReference(Channel.class, channelId);

        try {
            final Assignee assignee = assigneeRepository.findByUserUserId(request.userId())
                    .orElseGet(() -> Assignee.assignAssignee(userRepository.findById(request.userId())
                            .orElseThrow(() -> new HouseworkException(ExceptionCode.USER_NOT_FOUND))));
            final Assignee saveAssignee = assigneeRepository.saveAndFlush(assignee);
            final Housework housework = Housework.of(
                    request.startDate(),
                    request.startTime(),
                    request.task(),
                    HouseworkCategory.parse(request.category()),
                    saveAssignee,
                    channel);
            houseworkRepository.save(housework);
        } catch (IllegalArgumentException exception) {
            throw new HouseworkException(ExceptionCode.HOUSEWORK_NOT_NULL);
        }
    }

    @Override
    public void updateHousework(final User loginUser, final Long houseworkId, final Long channelId,
                                final HouseworkRequest request) {
        channelValidator.validateExistChannel(channelId);
        houseworkValidator.validateExistHousework(houseworkId);
        final Housework housework = entityManager.getReference(Housework.class, houseworkId);
        houseworkValidator.validateEditableUser(housework, loginUser);
        try {
            final Assignee assignee = assigneeRepository.findByUserUserId(request.userId())
                    .orElseGet(() -> Assignee.assignAssignee(userRepository.findById(request.userId())
                            .orElseThrow(() -> new HouseworkException(ExceptionCode.USER_NOT_FOUND))));
            final Housework updateHousework = housework.update(request, assignee);
            houseworkRepository.save(updateHousework);
        } catch (IllegalArgumentException exception) {
            throw new HouseworkException(ExceptionCode.HOUSEWORK_NOT_NULL);
        }
    }

    @Override
    public void updateStatus(User loginUser, Long channelId, Long houseworkId) {
        channelValidator.validateExistChannel(channelId);
        houseworkValidator.validateExistHousework(houseworkId);
        final Housework housework = houseworkRepository.findByChannelChannelIdAndHouseworkId(channelId, houseworkId)
                .orElseThrow(() -> new HouseworkException(ExceptionCode.HOUSEWORK_NOT_NULL));
        houseworkValidator.validateEditableUser(housework, loginUser);
        housework.updateStatus();
        houseworkRepository.save(housework);
    }

    @Override
    public Map<String, Integer> calculateHouseworkStatisticsForWeek(Long channelId, LocalDate targetDate) {
        LocalDate startOfWeek = targetDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)); // 일 부터
        LocalDate endOfWeek = targetDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY)); // 토 까지

        int completedCount = houseworkRepository.countByStatusAndDateRange(
                channelId, Status.COMPLETE, startOfWeek, endOfWeek);

        int uncompletedCount = houseworkRepository.countByStatusAndDateRange(
                channelId, Status.UN_COMPLETE, startOfWeek, endOfWeek);

        Map<String, Integer> statistics = new HashMap<>();
        statistics.put("completeCount", completedCount);
        statistics.put("unCompletedCount", uncompletedCount);
        return statistics;
    }

    @Override
    public void deleteHousework(final User loginUser, final Long houseworkId, final Long channelId) {
        channelValidator.validateExistChannel(channelId);
        houseworkValidator.validateExistHousework(houseworkId);
        final Housework housework = houseworkRepository.findById(houseworkId).orElseThrow();
        houseworkValidator.validateEditableUser(housework, loginUser);
        try {
            houseworkRepository.delete(housework);
        } catch (IllegalArgumentException exception) {
            throw new HouseworkException(ExceptionCode.HOUSEWORK_NOT_NULL);
        }
    }
}
