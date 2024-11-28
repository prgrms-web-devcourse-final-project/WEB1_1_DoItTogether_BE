package com.doittogether.platform.business.housework;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.exception.housework.HouseworkException;
import com.doittogether.platform.business.channel.ChannelValidator;
import com.doittogether.platform.domain.entity.Assignee;
import com.doittogether.platform.domain.entity.Channel;
import com.doittogether.platform.domain.entity.Housework;
import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.infrastructure.persistence.channel.ChannelRepository;
import com.doittogether.platform.infrastructure.persistence.housework.AssigneeRepository;
import com.doittogether.platform.infrastructure.persistence.housework.HouseworkRepository;
import com.doittogether.platform.presentation.dto.housework.HouseworkRequest;
import com.doittogether.platform.presentation.dto.housework.HouseworkSliceResponse;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class HouseworkServiceImpl implements HouseworkService {

    private final EntityManager entityManager;
    private final HouseworkRepository houseworkRepository;
    private final ChannelRepository channelRepository;
    private final AssigneeRepository assigneeRepository;
    private final HouseworkValidator houseworkValidator;
    private final ChannelValidator channelValidator;

    @Transactional(readOnly = true)
    public HouseworkSliceResponse findAllByChannelId(final User loginUser, final Long lastHouseworkId,
                                                     final Long channelId,
                                                     final
                                                     PageRequest pageRequest) {
        channelValidator.validateExistChannel(channelId);
        final Slice<Housework> houseworks = houseworkRepository.findAllByChannelId(lastHouseworkId, channelId,
                loginUser.retrieveUserId(),
                pageRequest);
        return HouseworkSliceResponse.from(houseworks);
    }

    @Override
    @Transactional(readOnly = true)
    public HouseworkSliceResponse findAllByChannelIdAndAssigneeId(final Long lastHouseworkId, final Long channelId,
                                                                  final Long assigneeId, final
                                                                  PageRequest pageRequest) {
        channelValidator.validateExistChannel(channelId);
        final Slice<Housework> houseworks = houseworkRepository.findAllByChannelIdAndAssigneeId(channelId, assigneeId,
                pageRequest);
        return HouseworkSliceResponse.from(houseworks);
    }

    @Override
    public void addHousework(final Long channelId, final HouseworkRequest request) {
        channelValidator.validateExistChannel(channelId);
        final Channel channel = entityManager.getReference(Channel.class, channelId);
        try {
            final Assignee assignee = entityManager.getReference(Assignee.class, request.userId());
            final Housework housework = Housework.of(
                    request.startDateTime(),
                    request.task(),
                    request.category(),
                    Assignee.assignAssignee(assignee.retrieveUser()),
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
            final Assignee assignee = assigneeRepository.findByUserUserId(request.userId());
            final Housework updateHousework = housework.update(request, assignee);
            houseworkRepository.save(updateHousework);
        } catch (IllegalArgumentException exception) {
            throw new HouseworkException(ExceptionCode.HOUSEWORK_NOT_NULL);
        }
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
