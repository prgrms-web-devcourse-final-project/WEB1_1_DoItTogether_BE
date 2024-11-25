package com.doittogether.platform.business.invite;

import com.doittogether.platform.business.redis.RedisSingleDataService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class InviteLinkServiceImpl implements InviteLinkService {

    private final RedisSingleDataService redisSingleDataService;

    private static final String INVITE_LINK_PREFIX = "invite:";
    private static final int INVITE_LINK_TTL_MINUTES = 10;

    private String baseInviteUrl;

    @Value("${server.port:8080}")
    private String serverPort;

    @PostConstruct
    private void initBaseInviteUrl() {
        try {
            String ipAddress = InetAddress.getLocalHost().getHostAddress();
            this.baseInviteUrl = String.format("http://%s:%s/invite/", ipAddress, serverPort);
        } catch (Exception e) {
            // TODO : 공통 API 로 수정 필요
            throw new RuntimeException("서버 IP 주소를 가져오는 데 실패했습니다.", e);
        }
    }

    @Override
    public String generateInviteLink(Long channelId) {
        // 1. 기존 초대 코드 조회
        String existingInviteCode = findInviteCodeByChannelId(channelId);

        if (existingInviteCode != null) {
            // 기존 초대 코드의 TTL 갱신
            String redisKey = INVITE_LINK_PREFIX + existingInviteCode;
            redisSingleDataService.setSingleData(redisKey, channelId.toString(), Duration.ofMinutes(INVITE_LINK_TTL_MINUTES));
            return baseInviteUrl + existingInviteCode;
        }

        // 2. 새로운 초대 코드 생성
        String newInviteCode = RandomAuthCode.generate();
        String redisKey = INVITE_LINK_PREFIX + newInviteCode;
        redisSingleDataService.setSingleData(redisKey, channelId.toString(), Duration.ofMinutes(INVITE_LINK_TTL_MINUTES));
        return baseInviteUrl + newInviteCode;
    }

    @Override
    public Long validateInviteLink(String inviteCode) {
        String redisKey = INVITE_LINK_PREFIX + inviteCode;
        String channelId = redisSingleDataService.getSingleData(redisKey);

        if (channelId == null || channelId.isEmpty()) {
            // TODO : 공통 API 로 수정 필요
            throw new IllegalArgumentException("유효하지 않거나 만료된 초대 코드입니다.");
        }

        return Long.parseLong(channelId);
    }

    /**
     * Redis에서 channelId와 연결된 초대 코드를 검색
     *
     * @param channelId 채널 ID
     * @return 초대 코드 (없으면 null 반환)
     */
    private String findInviteCodeByChannelId(Long channelId) {
        for (String key : redisSingleDataService.getKeys(INVITE_LINK_PREFIX + "*")) {
            String value = redisSingleDataService.getSingleData(key);
            if (value != null && value.equals(channelId.toString())) {
                return key.replace(INVITE_LINK_PREFIX, ""); // 초대 코드 반환
            }
        }
        return null;
    }
}
