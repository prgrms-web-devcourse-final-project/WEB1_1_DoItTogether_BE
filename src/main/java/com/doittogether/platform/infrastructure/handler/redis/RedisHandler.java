package com.doittogether.platform.infrastructure.handler.redis;

import com.doittogether.platform.common.config.RedisConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisHandler {

    private final RedisConfig redisConfig;

    public ListOperations<String, Object> getListOperations() {
        return redisConfig.redisTemplate().opsForList();
    }

    public ValueOperations<String, Object> getValueOperations() {
        return redisConfig.redisTemplate().opsForValue();
    }

    public int executeOperation(Runnable operation) {
        try {
            operation.run();
            return 1;
        } catch (Exception e) {
            log.error("Redis 작업 오류 발생 : {}", e.getMessage());
            return 0;
        }
    }

    /**
     * Redis에서 특정 패턴에 해당하는 키 목록 반환
     *
     * @param pattern Redis 키 패턴 (예: "invite:*")
     * @return 패턴에 매칭되는 키 Set
     */
    public Set<String> getKeys(String pattern) {
        RedisTemplate<String, Object> redisTemplate = redisConfig.redisTemplate();

        return redisTemplate.keys(pattern);
    }
}
