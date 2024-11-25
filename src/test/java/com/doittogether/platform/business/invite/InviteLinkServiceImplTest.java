package com.doittogether.platform.business.invite;

import com.doittogether.platform.business.redis.RedisSingleDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class InviteLinkServiceImplTest {

    @Mock
    private RedisSingleDataService redisSingleDataService;

    @Spy
    @InjectMocks
    private InviteLinkServiceImpl inviteLinkService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // ReflectionTestUtils를 사용하여 @Value 필드에 값을 설정
        ReflectionTestUtils.setField(inviteLinkService, "inviteLinkUrl", "localhost:8080");
        ReflectionTestUtils.setField(inviteLinkService, "inviteLinkTtlMinutes", 10);

        // initBaseInviteUrl() 호출
        ReflectionTestUtils.invokeMethod(inviteLinkService, "initBaseInviteUrl");
    }

    @Test
    void 기본_초대_링크_URL_초기화_테스트() {
        // baseInviteUrl이 올바르게 초기화되었는지 검증
        String expectedUrl = "http://localhost:8080/";
        assertEquals(expectedUrl, ReflectionTestUtils.getField(inviteLinkService, "baseInviteUrl"));
    }

    @Test
    void 초대_링크_TTL_값_검증_테스트() {
        // inviteLinkTtlMinutes가 설정된 값을 가지고 있는지 검증
        int expectedTtl = 10;
        assertEquals(expectedTtl, ReflectionTestUtils.getField(inviteLinkService, "inviteLinkTtlMinutes"));
    }

    @Test
    void 기존_초대_링크_존재_시_초대_링크_생성_테스트() {
        Long channelId = 123L;
        String existingInviteLink = "existingCode";
        String redisKey = "invite:" + existingInviteLink;

        // Spy 객체 내부 메서드 Mock 설정
        doReturn(existingInviteLink).when(inviteLinkService).findInviteLinkByChannelId(channelId);

        // Redis 호출 Mock 설정
        Mockito.when(redisSingleDataService.setSingleData(eq(redisKey), eq(channelId.toString()), eq(Duration.ofMinutes(10))))
                .thenReturn(1);

        String result = inviteLinkService.generateInviteLink(channelId);

        assertEquals("http://localhost:8080/" + existingInviteLink, result);
        verify(redisSingleDataService).setSingleData(eq(redisKey), eq(channelId.toString()), eq(Duration.ofMinutes(10)));
    }

    @Test
    void 유효한_초대_링크_검증_테스트() {
        String inviteLink = "validCode";
        String redisKey = "invite:" + inviteLink;
        String channelId = "123";

        when(redisSingleDataService.getSingleData(redisKey)).thenReturn(channelId);

        Long result = inviteLinkService.validateInviteLink(inviteLink);

        assertNotNull(result);
        assertEquals(123L, result);
        verify(redisSingleDataService).getSingleData(redisKey);
    }
}
