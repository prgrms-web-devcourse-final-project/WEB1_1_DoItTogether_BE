package com.doittogether.platform.presentation.dto.user;

import com.doittogether.platform.domain.entity.ProfileImage;

public record OAuth2UserDTO(
        String name,
        String email,
        String socialId,
        String profileImage
) {
    public static OAuth2UserDTO of(String nickName, String email, String socialId, ProfileImage profileImage) {
        return new OAuth2UserDTO(
                nickName,
                email,
                socialId,
                profileImage.getUrl()
        );
    }
}
