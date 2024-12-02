package com.doittogether.platform.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")  // 모든 경로에 대해 CORS 설정
//                .allowedOrigins("http://localhost:3000")  // 요청을 허용할 도메인
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")  // 허용할 HTTP 메서드
//                .allowedHeaders("*")  // 허용할 헤더
//                .allowCredentials(true)  // 인증 정보(쿠키 등) 전송 허용
//                .maxAge(3600);  // CORS Pre-flight 요청의 캐시 기간
//    }
}
