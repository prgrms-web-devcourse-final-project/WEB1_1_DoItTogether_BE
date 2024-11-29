package com.doittogether.platform.common.auth.filter;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.common.exception.TemporaryLoginException;
import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.infrastructure.persistence.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class TemporaryAuthenticationFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.startsWith("/api/")) {
            String token = request.getHeader("Authorization");
            if (token != null && !token.isBlank()) {
                token = token.substring(7);
                User temporaryUser = userRepository.findByEmail(token).orElseThrow(() ->
                        new TemporaryLoginException(ExceptionCode.TEMPORARY_USER_NOT_FOUND));

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        temporaryUser, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}
