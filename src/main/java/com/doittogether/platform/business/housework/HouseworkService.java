package com.doittogether.platform.business.housework;

import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.presentation.dto.housework.HouseworkRequest;
import com.doittogether.platform.presentation.dto.housework.HouseworkResponse;
import com.doittogether.platform.presentation.dto.housework.HouseworkSliceResponse;
import java.time.LocalDate;
import org.springframework.data.domain.Pageable;

public interface HouseworkService {
    public HouseworkSliceResponse findAllByChannelIdAndTargetDate(final User loginUser, final Long channelId,
                                                                  final LocalDate targetDate, final Pageable pageable);

    public HouseworkSliceResponse findAllByChannelIdAndTargetDateAndAssigneeId(final Long channelId,
                                                                               final LocalDate targetDate,
                                                                               final Long assigneeId,
                                                                               final Pageable pageable);

    public void addHousework(final Long channelId, final HouseworkRequest request);

    public void updateHousework(final User loginUser, final Long houseworkId, final Long channelId,
                                final HouseworkRequest request);

    public void deleteHousework(final User loginUser, final Long houseworkId, final Long channelId);

    public HouseworkResponse findHouseworkByChannelIdAndHouseworkId(final User user, final Long houseworkId, final Long channelId);
}
