package dev.tolkach.methodologiesservice.adapter.out.security;

import dev.tolkach.methodologiesservice.application.port.out.TokenBlacklistPort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisTokenBlacklistAdapter implements TokenBlacklistPort {

    private final StringRedisTemplate redisTemplate;

    public RedisTokenBlacklistAdapter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean isBlacklisted(String jti) {

        Boolean exists = redisTemplate.hasKey("token_blacklist:" + jti);

        return exists != null && exists;
    }
}
