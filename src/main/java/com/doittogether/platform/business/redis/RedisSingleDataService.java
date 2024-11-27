package com.doittogether.platform.business.redis;

import java.time.Duration;
import java.util.Set;

public interface RedisSingleDataService {
    int storeData(String key, Object value);
    int storeDataWithExpiration(String key, Object value, Duration duration);
    String fetchData(String key);
    int removeData(String key);
    Set<String> findKeysByPattern(String pattern);
}
