package com.sharetravel.global.auth.jwt.repository;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RefreshTokenRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public void saveRefreshTokenById(String refreshTokenId, String refreshToken, long duration) {
        redisTemplate.opsForValue().set(refreshTokenId, refreshToken, duration, TimeUnit.MILLISECONDS);
    }

    public Optional<String> findRefreshTokenById(String refreshTokenId) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(refreshTokenId));
    }

    public void deleteByKey(String key) { // userId 또는 refreshTokenId
        redisTemplate.delete(key);
    }

    public void addRefreshTokenIdByUserId(String userId, String refreshTokenId) {
        redisTemplate.opsForSet().add(userId, refreshTokenId);
    }

    public Set<String> findAllRefreshTokenIdsByUserId(String userId) {
        return redisTemplate.opsForSet().members(userId);
    }
}
