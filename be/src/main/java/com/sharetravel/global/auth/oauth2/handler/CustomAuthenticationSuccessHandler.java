package com.sharetravel.global.auth.oauth2.handler;

import static com.sharetravel.global.auth.jwt.utils.TokenUtils.getAccessTokenCookie;
import static com.sharetravel.global.auth.jwt.utils.TokenUtils.getRefreshTokenIdCookie;

import com.sharetravel.global.auth.jwt.service.AccessTokenService;
import com.sharetravel.global.auth.jwt.service.RefreshTokenService;
import com.sharetravel.global.auth.oauth2.dto.CustomOAuth2User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {

        String userId = getUserIdFromAuth(authentication);

        String accessToken = accessTokenService.createAccessToken(userId);
        String refreshTokenId = refreshTokenService.createRefreshToken(userId);

        response.addCookie(getAccessTokenCookie(accessToken));
        response.addCookie(getRefreshTokenIdCookie(refreshTokenId));

        response.sendRedirect(System.getenv("CLIENT_URL"));
    }

    private String getUserIdFromAuth(Authentication authentication) {
        OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        CustomOAuth2User oAuth2User = (CustomOAuth2User) auth2AuthenticationToken.getPrincipal();
        return oAuth2User.getUserId();
    }
}
