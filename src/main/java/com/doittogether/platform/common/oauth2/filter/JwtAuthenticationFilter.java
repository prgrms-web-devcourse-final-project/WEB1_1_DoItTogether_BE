package com.doittogether.platform.common.oauth2.filter;

import com.doittogether.platform.application.global.code.ExceptionCode;
import com.doittogether.platform.application.global.exception.user.UserException;
import com.doittogether.platform.common.config.jwt.JwtProvider;
import com.doittogether.platform.common.config.jwt.JwtValidationType;
import com.doittogether.platform.common.config.jwt.UserAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final static String TOKEN_PREFIX = "Bearer ";
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        log.info("Starting JwtAuthenticationFilter for URI: {}", request.getRequestURI());

        log.debug("== Request Headers ==========================================");
        request.getHeaderNames().asIterator().forEachRemaining(headerName ->
                log.debug("{}: {}", headerName, request.getHeader(headerName))
        );
        log.debug("== Request Headers ==========================================");

        try {
            final String token = getJwtFromRequest(request);
            log.debug("Extracted JWT: {}", token);

            if (jwtProvider.validateToken(token) == JwtValidationType.VALID_TOKEN) {
                final Long memberId = jwtProvider.getUserFromJwt(token);
                log.info("Valid JWT for memberId: {}", memberId);
                UserAuthentication authentication = new UserAuthentication(memberId.toString(), null, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("Authentication set in SecurityContextHolder for memberId: {}", memberId);
            }
        } catch (Exception exception) {
            log.error("Error occurred during JWT processing: {}", exception.getMessage(), exception);
            throw new UserException(ExceptionCode._INTERNAL_SERVER_ERROR);
        }
        filterChain.doFilter(request, response);
        log.info("Completed JwtAuthenticationFilter for URI: {}", request.getRequestURI());
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
