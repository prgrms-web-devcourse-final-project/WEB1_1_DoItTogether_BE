package com.doittogether.platform.business.invite;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.exception.redis.InviteException;
import com.doittogether.platform.business.redis.RedisSingleDataService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class InviteLinkServiceImpl implements InviteLinkService {

    private final RedisSingleDataService redisSingleDataService;

    private static final String INVITE_LINK_PREFIX = "invite:";

    private final String inviteLinkUrl;

    private final int inviteLinkTtlMinutes;

    private String baseInviteUrl;

    public InviteLinkServiceImpl (
            RedisSingleDataService redisSingleDataService,
            @Value("${spring.data.redis.invite-link.url}") String inviteLinkUrl,
            @Value("${spring.data.redis.invite-link.ttl-minutes:10}") int inviteLinkTtlMinutes
    ) {
        this.redisSingleDataService = redisSingleDataService;
        this.inviteLinkUrl = inviteLinkUrl;
        this.inviteLinkTtlMinutes = inviteLinkTtlMinutes;
    }

    @PostConstruct
    private void initBaseInviteUrl() {
        try {
            this.baseInviteUrl = String.format("http://%s/", inviteLinkUrl);
        } catch (Exception e) {
            throw new InviteException(ExceptionCode.INVITE_LINK_GENERATION_FAILED);
        }
    }

    @Override
    public String generateInviteLink(Long channelId) {
        try {
            // 1. 기존 초대 링크 조회
            String existingInviteLink = findInviteLinkByChannelId(channelId);

            if (existingInviteLink != null) {
                // 기존 초대 링크의 TTL 갱신
                String redisKey = INVITE_LINK_PREFIX + existingInviteLink;
                redisSingleDataService.storeDataWithExpiration(redisKey, channelId.toString(), Duration.ofMinutes(inviteLinkTtlMinutes));
                return baseInviteUrl + existingInviteLink;
            }

            // 2. 새로운 초대 링크 생성
            String newInviteLink = RandomAuthCode.generate();
            String redisKey = INVITE_LINK_PREFIX + newInviteLink;
            redisSingleDataService.storeDataWithExpiration(redisKey, channelId.toString(), Duration.ofMinutes(inviteLinkTtlMinutes));
            return baseInviteUrl + newInviteLink;
        } catch (Exception e) {
            throw new InviteException(ExceptionCode.INVITE_LINK_GENERATION_FAILED);
        }
    }

    @Override
    public Long validateInviteLink(String inviteLink) {
        try {
            String redisKey = INVITE_LINK_PREFIX + inviteLink;
            String channelId = redisSingleDataService.fetchData(redisKey);

            if (channelId == null || channelId.isEmpty()) {
                throw new InviteException(ExceptionCode.INVITE_LINK_INVALID);
            }

            return Long.parseLong(channelId);
        } catch (NumberFormatException e) {
            throw new InviteException(ExceptionCode.INVITE_LINK_CHANNEL_ID_PARSE_FAILED);
        }
    }

    /**
     * Redis에서 channelId와 연결된 초대 코드를 검색
     *
     * @param channelId 채널 ID
     * @return 초대 코드 (없으면 null 반환)
     */
    public String findInviteLinkByChannelId(Long channelId) {
        try {
            for (String key : redisSingleDataService.findKeysByPattern(INVITE_LINK_PREFIX + "*")) {
                String value = redisSingleDataService.fetchData(key);
                if (value != null && value.equals(channelId.toString())) {
                    return key.replace(INVITE_LINK_PREFIX, ""); // 초대 코드 반환
                }
            }
            return null;
        } catch (Exception e) {
            throw new InviteException(ExceptionCode.REDIS_KEY_SEARCH_FAILED);
        }
    }
}
