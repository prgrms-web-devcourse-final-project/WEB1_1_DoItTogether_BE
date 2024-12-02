package com.doittogether.platform.business.oauth2;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.exception.UserException.UserException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomOAuth2AccessTokenResponseClient implements OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {

    public static final String REFRESH_TOKEN_KEY = "refresh_token";
    public static final String ACCESS_TOKEN_KEY = "access_token";
    public static final String EXPIRES_IN = "expires_in";

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String tokenUri;

    @Override
    public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest authorizationCodeGrantRequest) {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("code", authorizationCodeGrantRequest.getAuthorizationExchange().getAuthorizationResponse().getCode());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                tokenUri,
                HttpMethod.POST,
                request,
                String.class
        );

        String responseBody = response.getBody();

        if (responseBody == null) {
            throw new UserException(ExceptionCode.KAKAO_RESPONSE_NOT_FOUD);
        }

        String accessToken = parseJsonForAccessToken(responseBody);
        String refreshToken = parseJsonForRefreshToken(responseBody);

        long expiresIn = parseJsonForExpiresIn(responseBody);

        return OAuth2AccessTokenResponse.withToken(accessToken)
                .tokenType(OAuth2AccessToken.TokenType.BEARER)
                .expiresIn(expiresIn)
                .additionalParameters(Map.of(REFRESH_TOKEN_KEY, refreshToken))
                .build();
    }

    // JSON 응답에서 access_token 추출
    private String parseJsonForAccessToken(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            return jsonNode.get(ACCESS_TOKEN_KEY).asText();
        } catch (Exception e) {
            throw new UserException(ExceptionCode.TOKEN_NOT_FOUND);
        }
    }

    // JSON 응답에서 refresh_token 추출
    private String parseJsonForRefreshToken(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            return jsonNode.get(REFRESH_TOKEN_KEY).asText();
        }catch (Exception e) {
            throw new UserException(ExceptionCode.TOKEN_NOT_FOUND);
        }
    }

    // JSON 응답에서 expires_in 추출
    private long parseJsonForExpiresIn(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
                return jsonNode.get(EXPIRES_IN).asLong();
        } catch (Exception e) {
            throw new UserException(ExceptionCode.EXPIRES_IN_NOT_FOUND);
        }
    }
}
