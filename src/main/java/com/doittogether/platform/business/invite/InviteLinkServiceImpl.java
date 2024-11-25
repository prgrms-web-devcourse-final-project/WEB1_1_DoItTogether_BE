package com.doittogether.platform.business.invite;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class InviteLinkServiceImpl implements InviteLinkService {

    private final RedisTemplate<String, String> redisTemplate;

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
        String existingInviteCode = findInviteCodeByChannelId(channelId);

        if (existingInviteCode != null) {
            String redisKey = INVITE_LINK_PREFIX + existingInviteCode;
            redisTemplate.expire(redisKey, INVITE_LINK_TTL_MINUTES, TimeUnit.MINUTES);
            return baseInviteUrl + existingInviteCode;
        }

        String newInviteCode = RandomAuthCode.generate();
        String redisKey = INVITE_LINK_PREFIX + newInviteCode;
        redisTemplate.opsForValue().set(redisKey, channelId.toString(), INVITE_LINK_TTL_MINUTES, TimeUnit.MINUTES);
        return baseInviteUrl + newInviteCode;
    }

    @Override
    public Long validateInviteLink(String inviteCode) {
        String redisKey = INVITE_LINK_PREFIX + inviteCode;
        String channelId = redisTemplate.opsForValue().get(redisKey);

        if (channelId == null) {
            // TODO : 공통 API 로 수정 필요
            throw new IllegalArgumentException("유효하지 않거나 만료된 초대 코드입니다.");
        }

        return Long.parseLong(channelId);
    }

    /**
     * 해당 channelId에 이미 생성된 초대 코드를 Redis에서 검색
     *
     * @param channelId 채널 ID
     * @return 초대 코드 (없으면 null 반환)
     */
    private String findInviteCodeByChannelId(Long channelId) {
        // Redis에서 모든 invite:* 키를 검색
        for (String key : redisTemplate.keys(INVITE_LINK_PREFIX + "*")) {
            String value = redisTemplate.opsForValue().get(key);
            if (value != null && value.equals(channelId.toString())) {
                return key.replace(INVITE_LINK_PREFIX, ""); // 초대 코드 반환
            }
        }
        return null;
    }
}
