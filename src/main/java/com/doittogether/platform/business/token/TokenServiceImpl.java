package com.doittogether.platform.business.token;

import com.doittogether.platform.business.redis.RedisSingleDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenServiceImpl implements TokenService {

    private final RedisSingleDataService redisSingleDataService;

    @Override
    public int refreshTokenStoreData(Long kakaoId, String refreshToken, Duration duration) {
        String userIdStr = "user_refresh_"+String.valueOf(kakaoId);
        redisSingleDataService.storeDataWithExpiration(userIdStr, refreshToken, duration);

        return 0;
    }

    public String refreshTokenFetchData(Long kakaoId) {
        String userIdStr = "user_refresh_"+String.valueOf(kakaoId);

        return redisSingleDataService.fetchData(userIdStr);
    }
}
