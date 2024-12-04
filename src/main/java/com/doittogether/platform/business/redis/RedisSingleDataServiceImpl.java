package com.doittogether.platform.business.redis;

import com.doittogether.platform.common.config.RedisConfig;
import com.doittogether.platform.infrastructure.handler.redis.RedisHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisSingleDataServiceImpl implements RedisSingleDataService {

    private final RedisHandler redisHandler;
    private final RedisConfig redisConfig;

    /**
     * Redis 단일 데이터 값을 등록/수정합니다.
     *
     * @param key   : redis key
     * @param value : redis value
     * @return {int} 성공(1), 실패(0)
     */
    @Override
    public int storeData(String key, Object value) {
        return redisHandler.executeOperation(() -> redisHandler.getValueOperations().set(key, value));
    }

    /**
     * Redis 단일 데이터 값을 등록/수정합니다.(duration 값이 존재하면 메모리 상 유효시간을 지정합니다.)
     *
     * @param key      : redis key
     * @param value:   : redis value
     * @param duration : redis 값 메모리 상의 유효시간.
     * @return {int} 성공(1), 실패(0)
     */
    @Override
    public int storeDataWithExpiration(String key, Object value, Duration duration) {
        return redisHandler.executeOperation(() -> redisHandler.getValueOperations().set(key, value, duration));
    }

    /**
     * Redis 키를 기반으로 단일 데이터의 값을 조회합니다.
     *
     * @param key : redis key
     * @return {String} redis value 값 반환 or 미 존재시 null 반환
     */
    @Override
    public String fetchData(String key) {
        Object value = redisHandler.getValueOperations().get(key);
        if (value == null) return "";

        return String.valueOf(value);
    }

    /**
     * Redis 키를 기반으로 단일 데이터의 값을 삭제합니다.
     *
     * @param key : redis key
     * @return {int} 성공(1), 실패(0)
     */
    @Override
    public int removeData(String key) {
        return redisHandler.executeOperation(() -> redisConfig.redisTemplate().delete(key));
    }

    /**
     * Redis 키 패턴에 해당하는 키 목록 반환
     *
     * @param pattern 키 패턴 (예: "auth:*")
     * @return 키 Set
     */
    @Override
    public Set<String> findKeysByPattern(String pattern) {
        return redisHandler.getKeys(pattern);
    }
}
