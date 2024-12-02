package com.doittogether.platform.common.config;

import com.doittogether.platform.business.oauth2.CustomOAuth2AccessTokenResponseClient;
import com.doittogether.platform.business.oauth2.CustomOAuth2UserService;
import com.doittogether.platform.business.redis.RedisSingleDataServiceImpl;
import com.doittogether.platform.business.token.TokenService;
import com.doittogether.platform.common.token.filter.TokenFilter;
import com.doittogether.platform.common.token.filter.TokenUtil;
import com.doittogether.platform.common.oauth2.CustomLogoutSuccessHandler;
import com.doittogether.platform.common.oauth2.CustomSuccessHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;
    private final TokenUtil tokenUtil;
    private final CustomOAuth2AccessTokenResponseClient customAccessTokenResponseClient;
    private final TokenService tokenService;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // CSRF 비활성화: JWT를 사용하는 경우 CSRF 방어가 필요하지 않음
        http.csrf(csrf -> csrf.disable());

        // Form 로그인 비활성화: REST API 사용 시 필요 없음
        http.formLogin(form -> form.disable());

        // HTTP Basic 인증 비활성화: JWT 인증 방식 사용
        http.httpBasic(httpBasic -> httpBasic.disable());

        // 토큰 필터 추가
        http.addFilterBefore(new TokenFilter(tokenUtil, new RestTemplate(), new ObjectMapper(), tokenService), UsernamePasswordAuthenticationFilter.class);

        // OAuth2 로그인 설정
        http.oauth2Login(oauth2 -> oauth2
                .tokenEndpoint(tokenEndpoint -> tokenEndpoint
                        .accessTokenResponseClient(customAccessTokenResponseClient)) // Custom AccessToken Response Client
                .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                        .userService(customOAuth2UserService)) // Custom User Service
                .successHandler(customSuccessHandler) // OAuth2 로그인 성공 시 핸들러
        );

        // 로그아웃 설정
        http.logout(logout -> logout
                .logoutUrl("/api/auth/logout") // 로그아웃 요청 URL
                .logoutSuccessHandler(customLogoutSuccessHandler) // Kakao 로그아웃 API 호출 핸들러
                .invalidateHttpSession(true) // 세션 무효화
                .deleteCookies("JSESSIONID") // 쿠키 삭제
        );

        // 경로별 권한 설정
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/").permitAll() // 홈 경로 허용
                .anyRequest().authenticated() // 나머지 경로는 인증 필요
        );

        // 세션 설정: STATELESS (JWT 기반 인증을 위해 세션 사용 안 함)
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // CORS 설정
        http.cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOriginPatterns(List.of("*"));
            // configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000")); // 허용된 출처
            configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH")); // 허용된 메서드
            configuration.setAllowCredentials(true); // 자격 증명 허용
            configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept")); // 허용된 헤더
            //configuration.setAllowedHeaders(List.of("*"));
            configuration.setExposedHeaders(Arrays.asList("Authorization")); // 노출할 헤더
            configuration.setMaxAge(3600L); // Pre-flight 요청 캐시 시간
            return configuration;
        }));

        return http.build();
    }
}

