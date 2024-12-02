package com.doittogether.platform.common.token.filter;


import com.doittogether.platform.business.token.TokenService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String VALIDATE_TOKEN_URL = "https://kapi.kakao.com/v1/user/access_token_info";


    private final TokenUtil tokenUtil;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorization = request.getHeader(AUTHORIZATION_HEADER);

        // Authorization 헤더 확인
        if (authorization == null || !authorization.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = authorization.substring(BEARER_PREFIX.length());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);  // Bearer {accessToken}

        ResponseEntity<String> kakaoResponse = restTemplate.exchange(
                VALIDATE_TOKEN_URL,
                HttpMethod.GET,
                new org.springframework.http.HttpEntity<>(headers),
                String.class
        );

        JsonNode clientInfoResponse = objectMapper.readTree(kakaoResponse.getBody());

        // 응답 본문에서 "expires_in" 값을 추출
        Integer expiresIn = clientInfoResponse.get("expires_in").asInt();

        // accessToken 만료 확인
        if (expiresIn != null && expiresIn > 0) {
            filterChain.doFilter(request, response);
        } else {
            Long kakaoId = clientInfoResponse.get("id").asLong();
            String refreshToken = tokenService.refreshTokenFetchData(kakaoId);

            // refreshToken이 존재할 경우
            if (refreshToken != null) {

                JsonNode accessTokenResponse = objectMapper.readTree(tokenUtil.getAccessToken(refreshToken));

                String newAccessToken = accessTokenResponse.get("access_token").asText();

                response.setHeader("Authorization", "Bearer " + newAccessToken);

                filterChain.doFilter(request, response);

            } else { // refreshToken이 존재하지 않을 경우
                // refreshToken은 기간이 1달 이하로 남았을 경우 재요청이 필요

                tokenUtil.logout(accessToken);

                // JSON 응답 데이터 생성
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                // JSON 데이터 작성
                String jsonResponse = "{\"login\": false}";

                // JSON 데이터 전송
                response.getWriter().write(jsonResponse);

                return; // return이 맞는 걸까요..?
            }
        }
        filterChain.doFilter(request, response);
    }
}

