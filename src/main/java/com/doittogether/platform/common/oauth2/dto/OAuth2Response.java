package com.doittogether.platform.common.oauth2.dto;

public interface OAuth2Response {
    String getProvider();

    String getProviderId();

    String getNickname();

    String getName();

    String getEmail();

    String getProfileImage();
}
