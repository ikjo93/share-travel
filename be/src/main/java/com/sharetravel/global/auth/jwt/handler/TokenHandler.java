package com.sharetravel.global.auth.jwt.handler;

import static com.sharetravel.global.CommonUtil.getResponseEntity;
import static com.sharetravel.global.ServletUtil.*;

import com.sharetravel.global.auth.jwt.argumentresolver.RefreshTokenId;
import com.sharetravel.global.auth.jwt.dto.AccessTokenResponse;
import com.sharetravel.global.auth.jwt.exception.HackedTokenException;
import com.sharetravel.global.auth.jwt.exception.InvalidTokenException;
import com.sharetravel.global.auth.jwt.service.AccessTokenService;
import com.sharetravel.global.ApiResponseCode;
import com.sharetravel.global.ApiResponseMessage;
import com.sharetravel.global.auth.jwt.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenHandler {

    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/api/token/reissue")
    public ResponseEntity<AccessTokenResponse> reissue(@RefreshTokenId String refreshTokenId, HttpServletResponse response) {
        String refreshToken = refreshTokenService.validateAndGetToken(refreshTokenId);

        String renewedAccessToken = accessTokenService.renewAccessToken(refreshToken);

        /*
             리프레쉬 토큰을 이용하여 액세스 토큰을 재발급 받을 때마다 기존 리프레쉬 토큰 무효화 및 새로운 리프레쉬 토큰 발급
             이때, 새로 발급된 리프레쉬 토큰의 유효기간은 이전과 동일하며, 이전 리프레쉬 토큰에 접근 시 모든 리프레쉬 토큰 무효화
             이를 통해 리프레쉬 토큰 탈취 시 피해 파급 최소화
        */
        String renewedRefreshTokenId = refreshTokenService.renewRefreshToken(refreshTokenId, refreshToken);
        addTokenToCookie(response, renewedAccessToken, renewedRefreshTokenId);

        return ResponseEntity
                .status(ApiResponseCode.TOKEN_REFRESHED.getHttpStatusCode())
                .body(new AccessTokenResponse(renewedAccessToken, ApiResponseCode.TOKEN_REFRESHED.getCode(), ApiResponseCode.TOKEN_REFRESHED.getMessage()));
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
