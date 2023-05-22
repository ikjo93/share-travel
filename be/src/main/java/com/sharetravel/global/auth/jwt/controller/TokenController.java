package com.sharetravel.global.auth.jwt.controller;

import static com.sharetravel.global.api.ApiUtil.getResponseEntity;
import static com.sharetravel.global.auth.jwt.utils.TokenUtils.getRefreshTokenIdCookie;

import com.sharetravel.global.auth.jwt.argumentresolver.RefreshTokenId;
import com.sharetravel.global.auth.jwt.dto.AccessTokenResponse;
import com.sharetravel.global.auth.jwt.exception.HackedTokenException;
import com.sharetravel.global.auth.jwt.exception.InvalidTokenException;
import com.sharetravel.global.auth.jwt.service.AccessTokenService;
import com.sharetravel.global.api.ApiResponseCode;
import com.sharetravel.global.api.ApiResponseMessage;
import com.sharetravel.global.auth.jwt.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenController {

    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/api/tokens")
    public ResponseEntity<AccessTokenResponse> reissue(@RefreshTokenId String refreshTokenId, HttpServletResponse response) {
        String refreshToken = refreshTokenService.validateAndGetToken(refreshTokenId);

        String renewedAccessToken = accessTokenService.renewAccessToken(refreshToken);

        /*
             리프레쉬 토큰을 이용하여 액세스 토큰을 재발급 받을 때마다 기존 리프레쉬 토큰 무효화 및 새로운 리프레쉬 토큰 발급
             이때, 새로 발급된 리프레쉬 토큰의 유효기간은 이전과 동일하며, 이전 리프레쉬 토큰에 접근 시 모든 리프레쉬 토큰 무효화
             이를 통해 리프레쉬 토큰 탈취 시 피해 파급 최소화
        */
        String renewedRefreshTokenId = refreshTokenService.renewRefreshToken(refreshTokenId, refreshToken);
        response.addCookie(getRefreshTokenIdCookie(renewedRefreshTokenId));

        ApiResponseCode apiResponseCode = ApiResponseCode.TOKEN_REFRESHED;
        return ResponseEntity
                .status(apiResponseCode.getHttpStatusCode())
                .body(
                    AccessTokenResponse.of(
                        renewedAccessToken,
                        apiResponseCode.getCode(),
                        apiResponseCode.getMessage())
                );
    }

    @DeleteMapping("/api/tokens")
    public ResponseEntity<ApiResponseMessage> logout(@RefreshTokenId String refreshTokenId) {
        refreshTokenService.deleteToken(refreshTokenId);
        return getResponseEntity(ApiResponseCode.USER_LOGOUT_SUCCESS);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiResponseMessage> handleInvalidTokenException() {
        return getResponseEntity(ApiResponseCode.TOKEN_INVALID);
    }

    @ExceptionHandler(HackedTokenException.class)
    public ResponseEntity<ApiResponseMessage> handleHackedTokenException() {
        return getResponseEntity(ApiResponseCode.TOKEN_HACKED);
    }
}
