package com.doittogether.platform.business.channel;

import com.doittogether.platform.presentation.dto.channel.request.*;
import com.doittogether.platform.presentation.dto.channel.response.*;

public interface ChannelService {

    ChannelRegisterResponse createChannel(ChannelRegisterRequest request);
    ChannelUpdateResponse updateChannelName(Long channelId, ChannelUpdateRequest request);
    ChannelUserListResponse getChannelUsers(Long channelId);
    ChannelInviteLinkResponse generateInviteLink(Long channelId);
    ChannelKickUserResponse kickUserFromChannel(Long channelId, ChannelKickUserRequest request);
    ChannelJoinResponse joinChannelViaInviteLink(ChannelJoinRequest request);
    ChannelHouseworkListResponse getHouseworkByDate(Long channelId, String targetDate);
}
