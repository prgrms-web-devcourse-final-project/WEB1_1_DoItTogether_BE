package com.doittogether.platform.business.token;

import java.time.Duration;

public interface TokenService {

    int refreshTokenStoreData(Long userId, String refreshToken, Duration duration);

    String refreshTokenFetchData(Long kakaoId);
}
