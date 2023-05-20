package com.sharetravel.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RedisInitializer implements CommandLineRunner {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void run(String... args) {
        RedisConnection redisConnection = redisTemplate.getConnectionFactory().getConnection();
        RedisServerCommands serverCommands = redisConnection.serverCommands();
        serverCommands.flushAll();
    }
}
