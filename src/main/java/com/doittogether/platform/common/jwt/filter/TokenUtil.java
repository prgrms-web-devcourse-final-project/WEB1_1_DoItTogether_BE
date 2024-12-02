package com.doittogether.platform.common.jwt.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TokenUtil {
    private final RestTemplate restTemplate;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String CLIENT_SECRET;

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String TOKEN_URI;

    //    @Value("${kakao.logout-uri}")
    private final String LOGOUT_URI = "https://kapi.kakao.com/v1/user/logout";

    public String getAccessToken(String refreshToken) {
        // 1. HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 2. 요청 파라미터 설정
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "refresh_token");
        params.put("client_id", CLIENT_ID);
        params.put("refresh_token", refreshToken);
        params.put("client_secret", CLIENT_SECRET);

        // 3. HttpEntity 생성
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(params, headers);

        // 4. REST API 호출
        ResponseEntity<String> response = restTemplate.exchange(
                TOKEN_URI,
                HttpMethod.POST,
                entity,
                String.class
        );

        // 5. 응답 처리
        return response.getBody();
    }

    public String logout(String accessToken) {
        // 1. 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.set("Authorization", "Bearer " + accessToken);

        // 2. 요청 엔티티 생성
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

        // 3. RestTemplate을 사용하여 요청 보내기
        ResponseEntity<String> response;

        System.out.println("LOGOUT_URI = " + LOGOUT_URI);

        try {
            response = restTemplate.exchange(
                    LOGOUT_URI,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
        } catch (Exception e) {
            throw new RuntimeException("카카오 로그아웃 요청 실패: " + e.getMessage());
        }

        // 4. 응답 처리
        if (response.getStatusCode().is2xxSuccessful()) {
            return "로그아웃 성공: " + response.getBody();
        } else {
            throw new RuntimeException("카카오 로그아웃 실패: " + response.getStatusCode());
        }
    }
}
