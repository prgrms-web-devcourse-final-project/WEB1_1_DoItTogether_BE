package com.doittogether.platform.common.auth.filter;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.response.ExceptionResponse;
import com.doittogether.platform.common.exception.TemporaryLoginException;
import com.doittogether.platform.domain.entity.User;
import com.doittogether.platform.infrastructure.persistence.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TemporaryAuthenticationFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.startsWith("/api/")) {
            try {
                String token = request.getHeader("Authorization");
                if (token != null && !token.isBlank()) {
                    token = token.substring(7).trim();
                    User temporaryUser = userRepository.findByEmail(token).orElseThrow(() ->
                            new TemporaryLoginException(ExceptionCode.TEMPORARY_USER_NOT_FOUND_2));

                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            temporaryUser, null, null);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    throw new TemporaryLoginException(ExceptionCode.TEMPORARY_USER_NOT_FOUND_1);
                }
            } catch (TemporaryLoginException e) {
                ExceptionResponse<Void> exceptionResponse = ExceptionResponse.onFailure(e.getExceptionCode());

                response.setCharacterEncoding("UTF-8");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write(new ObjectMapper().writeValueAsString(exceptionResponse));
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
