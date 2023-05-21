package com.sharetravel.global.auth.jwt.service;

import com.sharetravel.global.auth.jwt.exception.HackedTokenException;
import com.sharetravel.global.auth.jwt.exception.InvalidTokenException;
import com.sharetravel.global.auth.jwt.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RefreshTokenService extends TokenService {

    private static final long REFRESH_TOKEN_DURATION = 10 * 60_000; // 10 minutes -> milliseconds

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public String createRefreshToken(String userId) {
        String refreshToken = makeToken(userId, System.currentTimeMillis() + REFRESH_TOKEN_DURATION);
        String refreshTokenId = UUID.randomUUID().toString();
        refreshTokenRepository.saveRefreshTokenById(refreshTokenId, refreshToken, REFRESH_TOKEN_DURATION); // 리프레쉬 토큰 아이디별 리프레쉬 토큰 값 저장
        deleteRefreshTokensByUserId(userId);
        refreshTokenRepository.addRefreshTokenIdByUserId(userId, refreshTokenId); // 해당 사용자 이름으로 새롭게 발행된 리프레쉬 토큰 아이디 값 저장

        return refreshTokenId;
    }

    @Transactional
    public String validateAndGetToken(String refreshTokenId) {
        String refreshToken = refreshTokenRepository.findRefreshTokenById(refreshTokenId)
            .orElseThrow(() -> {
                throw new InvalidTokenException(); // 리프레쉬 토큰이 리프레쉬 토큰이 만료됐거나 존재하지 않는 경우(유효하지않은 리프레쉬 토큰 아이디로 접근한 경우)
            });

        Claims claims = getClaims(refreshToken);

        if (claims.get(INVALIDATE, Boolean.class)) { // 리프레쉬 토큰 재사용이 감지된 경우
            String userId = claims.getSubject();
            deleteRefreshTokensByUserId(userId);

            throw new HackedTokenException();
        }

        return refreshToken;
    }

    private void deleteRefreshTokensByUserId(String userId) {
        Set<String> refreshTokenIds = refreshTokenRepository.findAllRefreshTokenIdsByUserId(userId);
        for (String id : refreshTokenIds) { // 해당 사용자 이름으로 이전에 발행됐었던 모든 리프레쉬 토큰 값들 삭제
            refreshTokenRepository.deleteByKey(id);
        }

        refreshTokenRepository.deleteByKey(userId); // 해당 사용자 이름으로 발행된 모든 리프레쉬 토큰 아이디 set 삭제
    }

    @Transactional
    public String renewRefreshToken(String refreshTokenId, String refreshToken) {
        Claims claims = getClaims(refreshToken);
        String userId = claims.getSubject();
        long expiration = claims.getExpiration().getTime();
        long restDuration = expiration - System.currentTimeMillis();

        String renewRefreshToken = remakeToken(refreshToken, expiration);
        String renewRefreshTokenId = UUID.randomUUID().toString();

        // 기존에 사용자에게 부여됐었던 리프레쉬 토큰 값을 무효화
        String invalidToken = makeInvalidToken(refreshToken, expiration);
        refreshTokenRepository.saveRefreshTokenById(refreshTokenId, invalidToken, restDuration);

        // 새로운 리프레쉬 토큰 아이디와 토큰 값 저장
        refreshTokenRepository.addRefreshTokenIdByUserId(userId, renewRefreshTokenId);
        refreshTokenRepository.saveRefreshTokenById(renewRefreshTokenId, renewRefreshToken, restDuration);

        return renewRefreshTokenId;
    }

    private String makeInvalidToken(String token, long expiration) {
        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setClaims(getClaims(token))
            .claim(INVALIDATE, true)
            .setIssuedAt(new Date())
            .setExpiration(new Date(expiration))
            .signWith(SECRET_KEY)
            .compact();
    }

    @Transactional
    public void deleteToken(String refreshTokenId) {
        refreshTokenRepository.deleteByKey(refreshTokenId);
    }
}
