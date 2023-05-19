package com.sharetravel.global.auth.jwt.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AccessTokenResponse {

    private String accessToken;
    private String code;
    private String message;

    public static AccessTokenResponse of(String accessToken, String code, String message) {
        return new AccessTokenResponse(accessToken, code, message);
    }
}
