package com.doittogether.platform.presentation.dto.user;

public interface OAuth2Response {
    String getProvider();

    String getProviderId();

    String getNickname();

    String getName();

    String getEmail();

    String getProfileImage();
}
