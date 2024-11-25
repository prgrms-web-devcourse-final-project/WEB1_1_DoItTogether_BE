package com.doittogether.platform.business.invite;

public interface InviteLinkService {
    String generateInviteLink(Long channelId);
    Long validateInviteLink(String inviteCode);
}
