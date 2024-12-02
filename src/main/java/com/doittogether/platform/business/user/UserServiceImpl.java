package com.doittogether.platform.business.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

//    @Value("${kakao.logout-uri}")
    private final String LOGOUT_URI = "https://kapi.kakao.com/v1/user/logout";

    private final RestTemplate restTemplate;

//    @Override
//    public String logout(String accessToken) {
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
//        headers.set("Authorization", "Bearer " + accessToken);
//
//        // 2. 요청 엔티티 생성
//        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
//
//        // 3. RestTemplate을 사용하여 카카오 API 호출
//        ResponseEntity<String> response;
//
//        try {
//            response = restTemplate.exchange(
//                    LOGOUT_URI,
//                    HttpMethod.POST,
//                    requestEntity,
//                    String.class
//            );
//        } catch (Exception e) {
//            throw new RuntimeException("카카오 로그아웃 요청 실패: " + e.getMessage());
//        }
//
//        if (!response.getStatusCode().is2xxSuccessful()) {
//            throw new RuntimeException("카카오 로그아웃 실패: " + response.getStatusCode());
//        }
//
//        return response.getBody();
//    }
}
