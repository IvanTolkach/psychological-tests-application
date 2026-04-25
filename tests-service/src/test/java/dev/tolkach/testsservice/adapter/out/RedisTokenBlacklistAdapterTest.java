package dev.tolkach.testsservice.adapter.out;

import dev.tolkach.testsservice.adapter.out.security.RedisTokenBlacklistAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedisTokenBlacklistAdapterTest {

    @Mock
    StringRedisTemplate redisTemplate;

    RedisTokenBlacklistAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new RedisTokenBlacklistAdapter(redisTemplate);
    }

    @Test
    void isBlacklisted_true() {
        String jti = "token123";
        when(redisTemplate.hasKey("token_blacklist:" + jti)).thenReturn(Boolean.TRUE);

        assertTrue(adapter.isBlacklisted(jti));
    }

    @Test
    void isBlacklisted_false() {
        String jti = "token123";
        when(redisTemplate.hasKey("token_blacklist:" + jti)).thenReturn(Boolean.FALSE);

        assertFalse(adapter.isBlacklisted(jti));
    }

    @Test
    void isBlacklisted_nullReturnsFalse() {
        String jti = "token123";
        when(redisTemplate.hasKey("token_blacklist:" + jti)).thenReturn(null);

        assertFalse(adapter.isBlacklisted(jti));
    }
}
