package com.sharetravel.global.auth.oauth2.handler;

import static com.sharetravel.global.ServletUtil.*;

import com.sharetravel.global.auth.jwt.service.AccessTokenService;
import com.sharetravel.global.auth.jwt.service.RefreshTokenService;
import com.sharetravel.global.auth.oauth2.dto.OAuth2UserInfo;
import com.sharetravel.global.auth.oauth2.utils.OAuth2UserInfoUtil;
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

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoUtil.getOAuth2UserInfo(
            (OAuth2AuthenticationToken) authentication);

        String userId = oAuth2UserInfo.getUserId();

        String accessToken = accessTokenService.createAccessToken(userId);
        String refreshTokenId = refreshTokenService.createRefreshToken(userId);

        addTokenToCookie(response, accessToken, refreshTokenId);
        response.sendRedirect("http://localhost:3000");
    }
}
