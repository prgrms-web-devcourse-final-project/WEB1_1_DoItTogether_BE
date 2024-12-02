package com.doittogether.platform.business.oauth2;

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

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2AccessToken accessToken = userRequest.getAccessToken();
        String accessTokenValue = accessToken.getTokenValue();
        String refreshTokenValue = userRequest.getAdditionalParameters().get("refresh_token").toString();

        log.info("Access Token: {}", accessTokenValue);
        log.info("Refresh Token: {}", refreshTokenValue);

        OAuth2User oAuth2User = super.loadUser(userRequest);

        Long kakaoId = extractKakaoId(oAuth2User.getAttributes());
        if (kakaoId == null) {
            throw new IllegalArgumentException("KakaoId not found in the response");
        }

        String email = extractEmail(oAuth2User.getAttributes());
        if (email == null) {
            throw new IllegalArgumentException("Email not found in the response");
        }

        String nickName = extractNickname(oAuth2User.getAttributes());
        if (nickName == null) {
            throw new IllegalArgumentException("NickName not found in the response");
        }

        String tokenProfileImage = extractProfileImage(oAuth2User.getAttributes());
        if (tokenProfileImage == null) {
            throw new IllegalArgumentException("Profile image not found in the response");
        }

        ProfileImage profileImage = new ProfileImage(tokenProfileImage);

        User existData = userRepository.findBykakaoId(kakaoId);

        if (existData == null) {

            User user = User.of(kakaoId, nickName, email, profileImage);

            userRepository.save(user);
        }

        UserDTO userDTO = new UserDTO(kakaoId, nickName, email, "ROLE_USER", accessTokenValue, refreshTokenValue);
        return new CustomOAuth2User(userDTO);
    }

    // KakaoId 추출 메서드
    private Long extractKakaoId(Map<String, Object> attributes) {
        Object idObject = attributes.get("id");
        if (idObject instanceof Long) {
            return (Long) idObject; // Long 타입으로 캐스팅
        } else {
            throw new IllegalArgumentException("Kakao ID is not of type Long.");
        }
    }


    // Email 추출 메서드
    private String extractEmail(Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        if (kakaoAccount != null) {
            return (String) kakaoAccount.get("email");
        }
        return null; // 이메일이 없는 경우
    }

    // 닉네임 추출 메서드
    private String extractNickname(Map<String, Object> attributes) {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        if (properties != null) {
            return (String) properties.get("nickname");
        }
        return null; // 닉네임이 없는 경우
    }

    // 프로필 이미지 추출 메서드 (수정됨)
    private String extractProfileImage(Map<String, Object> attributes) {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        if (properties != null) {
            return (String) properties.get("profile_image");
        }
        return null; // 프로필 이미지가 없는 경우
    }
}

