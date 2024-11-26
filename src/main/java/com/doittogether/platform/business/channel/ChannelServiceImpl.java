package com.doittogether.platform.business.channel;

import com.doittogether.platform.presentation.dto.channel.request.ChannelJoinRequest;
import com.doittogether.platform.presentation.dto.channel.request.ChannelKickUserRequest;
import com.doittogether.platform.presentation.dto.channel.request.ChannelRegisterRequest;
import com.doittogether.platform.presentation.dto.channel.request.ChannelUpdateRequest;
import com.doittogether.platform.presentation.dto.channel.response.*;

public class ChannelServiceImpl implements ChannelService {

    @Override
    public ChannelRegisterResponse createChannel(ChannelRegisterRequest request) {

        ChannelRegisterRequest.toEntity(request);

        return null;
    }

    @Override
    public ChannelUpdateResponse updateChannelName(Long channelId, ChannelUpdateRequest request) {
        return null;
    }

    @Override
    public ChannelUserListResponse getChannelUsers(Long channelId) {
        return null;
    }

    @Override
    public ChannelInviteLinkResponse generateInviteLink(Long channelId) {
        return null;
    }

    @Override
    public ChannelKickUserResponse kickUserFromChannel(Long channelId, ChannelKickUserRequest request) {
        return null;
    }

    @Override
    public ChannelJoinResponse joinChannelViaInviteLink(ChannelJoinRequest request) {
        return null;
    }

    @Override
    public ChannelHouseworkListResponse getHouseworkByDate(Long channelId, String targetDate) {
        return null;
    }
}
