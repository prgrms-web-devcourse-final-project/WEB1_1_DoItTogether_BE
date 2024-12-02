package com.doittogether.platform.common.oauth2;

import com.doittogether.platform.business.token.TokenService;
import com.doittogether.platform.presentation.dto.OAuth.CustomOAuth2User;
import com.doittogether.platform.presentation.dto.OAuth.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenService TokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // 사용자 인증 정보 가져오기
        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authToken.getPrincipal();

        // 사용자 정보 가져오기
        UserDTO userDTO = customOAuth2User.getUserDTO();
        Long kakaoId = userDTO.getKakaoId();
        String accessToken = userDTO.getAccessToken();  // 액세스 토큰 정보 가져오기
        String refreshToken = userDTO.getRefreshToken();  // 리프레시 토큰 정보 (있으면)

        // Refresh Token 만료 기간 설정 (2달)
        Duration refreshTokenExpiration = Duration.ofHours(2 * 30 * 24); // 리프레시 토큰의 만료 기간 설정 (2달)

        // Refresh Token 저장
        TokenService.refreshTokenStoreData(kakaoId, refreshToken, refreshTokenExpiration);

        // Set Authorization header with Bearer token
        response.setHeader("Authorization", "Bearer " + accessToken);

        // JSON 응답 데이터 생성
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // JSON 데이터 작성
        String jsonResponse = "{\"login\": true}";

        // JSON 데이터 전송
        response.getWriter().write(jsonResponse);
    }
}

