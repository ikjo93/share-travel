package com.sharetravel.global;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharetravel.global.auth.jwt.exception.InvalidTokenException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

public class ServletUtil {

    private static final String ACCESS_TOKEN_HEADER_TYPE = "Bearer ";

    private static final String ACCESS_TOKEN_COOKIE_NAME = "auth";
    private static final String REFRESH_TOKEN_ID_COOKIE_NAME = "renew";

    private static final int ACCESS_TOKEN_COOKIE_DURATION = 5; // 10 seconds
    private static final int REFRESH_TOKEN_ID_COOKIE_DURATION = 600; // 600 seconds (= 10 minutes)

    public static String parseAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(ACCESS_TOKEN_HEADER_TYPE)) {
            return bearerToken.substring(ACCESS_TOKEN_HEADER_TYPE.length());
        }

        throw new InvalidTokenException();
    }

    public static String parseRefreshTokenId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(REFRESH_TOKEN_ID_COOKIE_NAME)) {
                return cookie.getValue();
            }
        }

        throw new InvalidTokenException();
    }

    public static void addTokenToCookie(HttpServletResponse response, String accessToken, String refreshTokenId) {
        response.addCookie(getAccessTokenCookie(accessToken));
        response.addCookie(getRefreshTokenIdCookie(refreshTokenId));
    }

    // TODO : https 적용 시 Secure 설정 필요
    private static Cookie getAccessTokenCookie(String accessToken) {
        Cookie refreshTokenCookie = new Cookie(ACCESS_TOKEN_COOKIE_NAME, accessToken);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setHttpOnly(false);
        refreshTokenCookie.setAttribute("SameSite", "Strict");
        refreshTokenCookie.setMaxAge(ACCESS_TOKEN_COOKIE_DURATION);

        return refreshTokenCookie;
    }

    // TODO : https 적용 시 Secure 설정 필요
    private static Cookie getRefreshTokenIdCookie(String refreshTokenId) {
        Cookie refreshTokenCookie = new Cookie(REFRESH_TOKEN_ID_COOKIE_NAME, refreshTokenId);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setAttribute("SameSite", "Strict");
        refreshTokenCookie.setMaxAge(REFRESH_TOKEN_ID_COOKIE_DURATION);

        return refreshTokenCookie;
    }

    public static void setApiResponse(HttpServletResponse response, ApiResponseCode apiResponseCode) throws IOException {
        setResponseHeader(response, apiResponseCode.getHttpStatusCode());
        setResponseBody(response, ApiResponseMessage.of(apiResponseCode.getCode(), apiResponseCode.getMessage()));
    }

    private static void setResponseHeader(HttpServletResponse response, int statusCode) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(statusCode);
    }

    private static void setResponseBody(HttpServletResponse response, Object value)
        throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        response.getWriter().write(
            Objects.requireNonNull(
                mapper.writeValueAsString(value)
            )
        );
    }
}