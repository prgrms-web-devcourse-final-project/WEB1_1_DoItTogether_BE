package com.doittogether.platform.business.redis;

import com.doittogether.platform.infrastructure.config.RedisConfig;
import com.doittogether.platform.infrastructure.handler.redis.RedisHandler;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {RedisConfig.class, RedisHandler.class, RedisSingleDataServiceImpl.class})
class RedisSingleDataServiceImplTest {

    @MockBean
    private RedisHandler redisHandler;

    @MockBean
    private RedisConfig redisConfig;

    @Autowired
    private RedisSingleDataServiceImpl redisSingleDataService;

    @Test
    void 레디스_데이터_저장_테스트() {
        when(redisHandler.executeOperation(Mockito.any()))
                .thenReturn(1);

        int result = redisSingleDataService.setSingleData("testKey", "testValue");

        assertEquals(1, result);
        verify(redisHandler).executeOperation(Mockito.any());
    }

    @Test
    void 레디스_데이터_저장_유효시간_포함_테스트() {
        when(redisHandler.executeOperation(Mockito.any()))
                .thenReturn(1);

        int result = redisSingleDataService.setSingleData("testKey", "testValue", Duration.ofMinutes(5));

        assertEquals(1, result);
        verify(redisHandler).executeOperation(Mockito.any());
    }

    @Test
    void 레디스_데이터_조회_성공_테스트() {
        ValueOperations<String, Object> valueOperations = Mockito.mock(ValueOperations.class);

        when(redisHandler.getValueOperations()).thenReturn(valueOperations);
        when(valueOperations.get("testKey")).thenReturn("testValue");

        String result = redisSingleDataService.getSingleData("testKey");

        assertEquals("testValue", result);
        verify(valueOperations).get("testKey");
    }

    @Test
    void 레디스_데이터_삭제_성공_테스트() {
        when(redisHandler.executeOperation(Mockito.any())).thenReturn(1);

        int result = redisSingleDataService.deleteSingleData("testKey");

        assertEquals(1, result);
    }

    @Test
    void 레디스_키_목록_조회_테스트() {
        Set<String> mockKeys = Set.of("auth:123", "auth:456", "auth:789");

        when(redisHandler.getKeys("auth:*")).thenReturn(mockKeys);

        Set<String> result = redisSingleDataService.getKeys("auth:*");

        assertNotNull(result); // 반환값이 null이 아닌지 확인
        assertEquals(3, result.size()); // 키 개수 확인
        assertTrue(result.contains("auth:123")); // 특정 키 포함 여부 확인
        verify(redisHandler).getKeys("auth:*"); // getKeys 호출 여부 검증
    }
}
