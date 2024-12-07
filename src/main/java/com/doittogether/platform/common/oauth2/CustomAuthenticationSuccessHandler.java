package com.doittogether.platform.common.oauth2;

import com.doittogether.platform.common.config.jwt.JwtProvider;
import com.doittogether.platform.common.config.jwt.UserAuthentication;
import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.infrastructure.persistence.user.UserRepository;
import com.doittogether.platform.common.oauth2.dto.CustomOAuth2User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Value("${front.url}")
    private String FRONT_SERVER;

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        final CustomOAuth2User customOAuth2UserDetails = (CustomOAuth2User) authentication.getPrincipal();
        final String socialId = customOAuth2UserDetails.getName();
        log.info("Authentication success for user: {}", authentication.getName());

        final User user = userRepository.findBySocialId(socialId);
        final UserAuthentication userAuthentication = new UserAuthentication(user.retrieveUserId(), null, null);
        final String token = jwtProvider.generateToken(userAuthentication);
        log.info("Generated JWT token for userId {}", user.retrieveUserId());

        response.setStatus(HttpServletResponse.SC_OK);
        final String redirectUrl = FRONT_SERVER + "?access_token=" + token;
        response.sendRedirect(redirectUrl);
    }
}
