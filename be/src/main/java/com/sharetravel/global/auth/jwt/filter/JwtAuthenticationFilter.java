package com.sharetravel.global.auth.jwt.filter;

import static com.sharetravel.global.servlet.ServletUtil.setApiResponse;
import static com.sharetravel.global.auth.jwt.utils.TokenUtils.parseAccessToken;

import com.sharetravel.global.auth.jwt.dto.JwtAuthenticationResult;
import com.sharetravel.global.auth.jwt.service.AccessTokenService;
import com.sharetravel.global.api.ApiResponseCode;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AccessTokenService accessTokenService;

    private final String[][] excludePathAndMethod = {
        {"/login", "GET"}, {"/oauth2", "GET"}, {"/api/token/reissue", "POST"}, {"/api/articles", "GET"},
        {"/api/travelkeywords", "GET"}
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws IOException, ServletException {

        try {
            String token = parseAccessToken(request);

            if (StringUtils.hasText(token)) {

                Claims claims = accessTokenService.validateAndGetClaims(token);

                JwtAuthenticationResult jwtAuthenticationResult = accessTokenService.getJwtAuthenticationResult(claims);
                jwtAuthenticationResult.setAuthenticated(true);
                jwtAuthenticationResult.setDetails(request.getRemoteAddr());

                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(jwtAuthenticationResult);
                SecurityContextHolder.setContext(securityContext);
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            setApiResponse(response, ApiResponseCode.TOKEN_INVALID);
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = request.getMethod();

        return Arrays.stream(excludePathAndMethod)
            .anyMatch(e -> path.startsWith(e[0]) && e[1].equals(method));
    }
}
