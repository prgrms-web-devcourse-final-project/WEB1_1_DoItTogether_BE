package com.doittogether.platform.common.oauth2;

import com.doittogether.platform.domain.entity.ProfileImage;
import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.infrastructure.persistence.user.ProfileImageRepository;
import com.doittogether.platform.infrastructure.persistence.user.UserRepository;
import com.doittogether.platform.common.oauth2.dto.CustomOAuth2User;
import com.doittogether.platform.common.oauth2.dto.KakaoOAuth2Response;
import com.doittogether.platform.common.oauth2.dto.OAuth2Response;
import com.doittogether.platform.common.oauth2.dto.OAuth2UserDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final ProfileImageRepository profileImageRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        log.info("Starting OAuth2 user loading process. Client registration ID: {}", registrationId);
        OAuth2Response oAuth2Response = null;

        if ("kakao".equals(registrationId)) {
            log.debug("Processing Kakao OAuth2 response");
            oAuth2Response = new KakaoOAuth2Response(oAuth2User.getAttributes());
        }

        final String socialId = oAuth2Response.getProvider() + "_" + oAuth2Response.getProviderId();
        log.info("Generated socialId: {}", socialId);

        final User existedUserOrNull = userRepository.findBySocialId(socialId);
        if (existedUserOrNull == null) {
            log.info("User not found. Creating a new user for socialId: {}", socialId);
            final String nickname = oAuth2Response.getNickname();
            final String email = oAuth2Response.getEmail();
            final String profileImageUrl = oAuth2Response.getProfileImage();

            log.debug("Creating new ProfileImage entity with URL: {}", profileImageUrl);
            final ProfileImage profileImage = profileImageRepository.saveAndFlush(
                    ProfileImage.from(profileImageUrl)
            );

            log.debug("Saving new User entity with nickname: {}, email: {}", nickname, email);
            final User user = userRepository.save(User.of(
                    nickname,
                    email,
                    socialId,
                    profileImage
            ));

            log.info("New user created successfully. User ID: {}", user.retrieveUserId());
            final OAuth2UserDTO oAuth2UserDTO = OAuth2UserDTO.of(
                    nickname,
                    email,
                    socialId,
                    profileImage
            );
            return new CustomOAuth2User(oAuth2UserDTO);
        }

        log.info("User already exists. SocialId: {}", socialId);
        final OAuth2UserDTO oAuth2UserDTO = OAuth2UserDTO.of(
                existedUserOrNull.retrieveNickName(),
                existedUserOrNull.retrieveEmail(),
                existedUserOrNull.retrieveSocialId(),
                existedUserOrNull.retrieveProfileImage()
        );

        log.debug("Returning existing user information for socialId: {}", socialId);
        return new CustomOAuth2User(oAuth2UserDTO);
    }
}
