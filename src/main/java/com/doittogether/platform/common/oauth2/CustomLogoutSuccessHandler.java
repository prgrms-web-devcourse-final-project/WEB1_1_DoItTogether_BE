package com.doittogether.platform.common.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final String LOGOUT_URI = "https://kapi.kakao.com/v1/user/logout";

    private final RestTemplate restTemplate;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException, IOException {

        // Authorization 헤더에서 토큰 가져오기
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Authorization header is missing or malformed.");
            return;
        }

        // "Bearer "를 제외한 액세스 토큰만 추출
        String accessToken = authorizationHeader.substring(7);

        if (accessToken == null || accessToken.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Access token is missing.");
            return;
        }

        // Kakao 로그아웃 API 호출
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> apiResponse = restTemplate.exchange(LOGOUT_URI, HttpMethod.POST, entity, String.class);
            if (apiResponse.getStatusCode().is2xxSuccessful()) {

                // JSON 응답 데이터 생성
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                // JSON 데이터 작성
                String jsonResponse = "{\"login\": false}";

                // JSON 데이터 전송
                response.getWriter().write(jsonResponse);

            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "카카오 로그아웃 실패");
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "카카오 로그아웃 호출 실패: " + e.getMessage());
        }
    }
}

