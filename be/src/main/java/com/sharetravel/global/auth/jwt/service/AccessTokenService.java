package com.sharetravel.global.auth.jwt.service;

import com.sharetravel.global.auth.jwt.dto.JwtAuthenticationResult;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AccessTokenService extends TokenService {

    private static final long ACCESS_TOKEN_DURATION = 2 * 60_000; // 2 minutes

    public String createAccessToken(String userId) {
        return makeToken(userId, System.currentTimeMillis() + ACCESS_TOKEN_DURATION);
    }

    public String renewAccessToken(String token) {
        return remakeToken(token, System.currentTimeMillis() + ACCESS_TOKEN_DURATION);
    }

    public Claims validateAndGetClaims(String token) {
        return getClaims(token);
    }

    public JwtAuthenticationResult getJwtAuthenticationResult(Claims claims) {
        return new JwtAuthenticationResult(
            Long.parseLong(claims.getSubject()), AuthorityUtils.NO_AUTHORITIES
        );
    }
}
