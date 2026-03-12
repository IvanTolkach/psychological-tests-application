package dev.tolkach.usersservice.adapter.out.security;

import dev.tolkach.usersservice.application.port.out.TokenBlacklistPort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;
import java.util.UUID;

@Component
public class RedisTokenBlacklistAdapter implements TokenBlacklistPort {

    private final StringRedisTemplate redisTemplate;

    public RedisTokenBlacklistAdapter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void blacklistToken(String jti, long ttl) {
        redisTemplate.opsForValue().set(
                "token_blacklist:" + jti,
                "true",
                Duration.ofSeconds(ttl)
        );
    }

    @Override
    public boolean isBlacklisted(String jti) {
        return redisTemplate.hasKey("token_blacklist:" + jti);
    }

    @Override
    public void storeUserToken(UUID userId, String jti) {
        redisTemplate.opsForSet().add("user_tokens:" + userId, jti);
    }

    @Override
    public Set<String> getUserTokens(UUID userId) {
        return redisTemplate.opsForSet().members("user_tokens:" + userId);
    }
}
