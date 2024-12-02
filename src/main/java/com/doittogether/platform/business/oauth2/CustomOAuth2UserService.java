package com.doittogether.platform.business.oauth2;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.exception.UserException.UserException;
import com.doittogether.platform.domain.entity.ProfileImage;
import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.infrastructure.persistence.UserRepository;
import com.doittogether.platform.presentation.dto.OAuth.CustomOAuth2User;
import com.doittogether.platform.presentation.dto.OAuth.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    public static final String REFRESH_TOKEN_KEY = "refresh_token";

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2AccessToken accessToken = userRequest.getAccessToken();
        String accessTokenValue = accessToken.getTokenValue();
        String refreshTokenValue = userRequest.getAdditionalParameters().get("refresh_token").toString();

        log.trace("Access Token: {}", accessTokenValue);
        log.trace("Refresh Token: {}", refreshTokenValue);

        OAuth2User oAuth2User = super.loadUser(userRequest);

        Long kakaoId = extractKakaoId(oAuth2User.getAttributes());
        String email = extractEmail(oAuth2User.getAttributes());
        String nickName = extractNickname(oAuth2User.getAttributes());
        String tokenProfileImage = extractProfileImage(oAuth2User.getAttributes());

        ProfileImage profileImage = ProfileImage.from(tokenProfileImage);

        User existData = userRepository.findBykakaoId(kakaoId);

        if (existData == null) {

            User user = User.of(kakaoId, nickName, email, profileImage);
            userRepository.save(user);
        }

        UserDTO userDTO = UserDTO.of(kakaoId, nickName, email, "ROLE_USER", accessTokenValue, refreshTokenValue);
        return new CustomOAuth2User(userDTO);
    }

    // KakaoId 추출 메서드
    private Long extractKakaoId(Map<String, Object> attributes) {
        Object idObject = attributes.get("id");
        if (idObject instanceof Long) {
            return (Long) idObject; // Long 타입으로 캐스팅
        } else {
            throw new UserException(ExceptionCode.KAKAO_ID_NOT_FOUND);
        }
    }


    // Email 추출 메서드
    private String extractEmail(Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        if (kakaoAccount != null) {
            return (String) kakaoAccount.get("email");
        }else{
            throw new UserException(ExceptionCode.KAKAO_EMAIL_NOT_FOUND);
        }
    }

    // 닉네임 추출 메서드
    private String extractNickname(Map<String, Object> attributes) {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        if (properties != null) {
            return (String) properties.get("nickname");
        }else{
            throw new UserException(ExceptionCode.KAKAO_NICKNAME_NOT_FOUND);
        }
    }

    // 프로필 이미지 추출 메서드 (수정됨)
    private String extractProfileImage(Map<String, Object> attributes) {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        if (properties != null) {
            return (String) properties.get("profile_image");
        }else{
            throw new UserException(ExceptionCode.KAKAO_PROFILE_IMAGE_NOT_FOUND);
        }
    }
}

