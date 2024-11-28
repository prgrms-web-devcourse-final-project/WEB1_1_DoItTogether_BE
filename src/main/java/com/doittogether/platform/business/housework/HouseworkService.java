package com.doittogether.platform.business.housework;

import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.presentation.dto.housework.HouseworkRequest;
import com.doittogether.platform.presentation.dto.housework.HouseworkSliceResponse;
import org.springframework.data.domain.PageRequest;

public interface HouseworkService {
    public HouseworkSliceResponse findAllByChannelId(final User loginUser, final Long lastHouseworkId, final Long channelId,
                                                     final PageRequest pageRequest);

    public HouseworkSliceResponse findAllByChannelIdAndAssigneeId(final Long lastHouseworkId, final Long channelId,
                                                                  final Long userId, final
                                                                  PageRequest pageRequest);

    public void addHousework(final Long channelId, final HouseworkRequest request);

    public void updateHousework(final User loginUser, final Long houseworkId, final Long channelId,
                                final HouseworkRequest request);

    public void deleteHousework(final User loginUser, final Long houseworkId, final Long channelId);
}
