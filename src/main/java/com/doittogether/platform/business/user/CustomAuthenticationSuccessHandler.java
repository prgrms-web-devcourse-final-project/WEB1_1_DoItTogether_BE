package com.doittogether.platform.business.user;

import com.doittogether.platform.common.config.jwt.JwtProvider;
import com.doittogether.platform.common.config.jwt.UserAuthentication;
import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.infrastructure.persistence.UserRepository;
import com.doittogether.platform.presentation.dto.user.CustomOAuth2User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        final CustomOAuth2User customOAuth2UserDetails = (CustomOAuth2User) authentication.getPrincipal();
        final String socialId = customOAuth2UserDetails.getName();
        final User user = userRepository.findBySocialId(socialId);
        final UserAuthentication userAuthentication = new UserAuthentication(user.retrieveUserId(), null, null);
        final String token = jwtProvider.generateToken(userAuthentication);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("{\"authorization\": \"" + token + "\"}");
    }
}
