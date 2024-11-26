package com.doittogether.platform.business.channel;

import com.doittogether.platform.presentation.dto.channel.request.*;
import com.doittogether.platform.presentation.dto.channel.response.*;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface ChannelService {

    ChannelRegisterResponse createChannel(String email, ChannelRegisterRequest request);
    ChannelUpdateResponse updateChannelName(String email, Long channelId, ChannelUpdateRequest request);
    ChannelUserListResponse getChannelUsers(String email, Long channelId, Pageable pageable);
    ChannelInviteLinkResponse generateInviteLink(Long channelId);
    ChannelKickUserResponse kickUserFromChannel(String email, Long channelId, ChannelKickUserRequest request);
    ChannelJoinResponse joinChannelViaInviteLink(ChannelJoinRequest request);
    ChannelHouseworkListResponse getHouseworkByDate(Long channelId, LocalDate targetDate, Pageable pageable);
}
