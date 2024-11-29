package com.doittogether.platform.business.invite;

import com.doittogether.platform.business.redis.RedisSingleDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Duration;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class InviteLinkServiceImplTest {

    @Mock
    private RedisSingleDataService redisSingleDataService;

    private InviteLinkServiceImpl inviteLinkService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // 직접 객체 생성 및 의존성 주입
        inviteLinkService = new InviteLinkServiceImpl(
                redisSingleDataService,
                10
        );

    }

    @Test
    void 생성자_초기화_테스트() {
        // 필드 값 검증
        assertEquals(10, ReflectionTestUtils.getField(inviteLinkService, "inviteLinkTtlMinutes"));
    }

    @Test
    void 기존_초대_링크_존재_시_초대_링크_생성_테스트() {
        Long channelId = 123L;
        String existingInviteLink = "existingCode";
        String redisKey = "invite:" + existingInviteLink;

        when(redisSingleDataService.findKeysByPattern("invite:*")).thenReturn(Set.of(redisKey));
        when(redisSingleDataService.fetchData(redisKey)).thenReturn(channelId.toString());

        when(redisSingleDataService.storeDataWithExpiration(eq(redisKey), eq(channelId.toString()), eq(Duration.ofMinutes(10))))
                .thenReturn(1);

        String result = inviteLinkService.generateInviteLink(channelId);

        assertEquals(existingInviteLink, result);
        verify(redisSingleDataService).storeDataWithExpiration(eq(redisKey), eq(channelId.toString()), eq(Duration.ofMinutes(10)));
        verify(redisSingleDataService, times(1)).fetchData(redisKey);
    }

    @Test
    void 유효한_초대_링크_검증_테스트() {
        String inviteLink = "validCode";
        String redisKey = "invite:" + inviteLink;
        String channelId = "123";

        when(redisSingleDataService.fetchData(redisKey)).thenReturn(channelId);

        Long result = inviteLinkService.validateInviteLink(inviteLink);

        assertNotNull(result);
        assertEquals(123L, result);
        verify(redisSingleDataService).fetchData(redisKey);
    }

}
