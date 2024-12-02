package com.doittogether.platform.presentation.dto.user;

import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KakaoOAuth2Response implements OAuth2Response {

    private final Map<String, Object> attributes;

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getName() {
        Map<String, String> profile = (Map<String, String>) attributes.get("kakao_account");
        return profile.get("name");
    }

    @Override
    public String getNickname() {
        Map<String, String> properties = (Map<String, String>) attributes.get("properties");
        return properties.get("nickname");
    }

    @Override
    public String getEmail() {
        Map<String, String> kakaoAccount = (Map<String, String>) attributes.get("kakao_account");
        return kakaoAccount.get("email");
    }

    @Override
    public String getProfileImage() {
        Map<String, String> properties = (Map<String, String>) attributes.get("properties");
        return properties.get("profile_image");
    }
}
