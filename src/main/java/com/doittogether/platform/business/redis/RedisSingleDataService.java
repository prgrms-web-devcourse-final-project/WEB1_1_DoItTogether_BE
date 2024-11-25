package com.doittogether.platform.business.redis;

import java.time.Duration;
import java.util.Set;

public interface RedisSingleDataService {
    int setSingleData(String key, Object value);
    int setSingleData(String key, Object value, Duration duration);
    String getSingleData(String key);
    int deleteSingleData(String key);
    Set<String> getKeys(String pattern);
}
