package dev.tolkach.usersservice.adapter.out;

import dev.tolkach.usersservice.adapter.out.security.RedisTokenBlacklistAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RedisTokenBlacklistAdapterTest {

    @Mock
    StringRedisTemplate redisTemplate;

    @Mock
    ValueOperations<String, String> valueOps;

    @Mock
    SetOperations<String, String> setOps;

    @InjectMocks
    RedisTokenBlacklistAdapter adapter;

    UUID userId = UUID.randomUUID();

    @BeforeEach
    void setup() {
        lenient().when(redisTemplate.opsForValue()).thenReturn(valueOps);
        lenient().when(redisTemplate.opsForSet()).thenReturn(setOps);
    }

    @Test
    void blacklistToken_callsOps() {
        adapter.blacklistToken("jti", 123);
        verify(valueOps).set("token_blacklist:jti", "true", Duration.ofSeconds(123));
    }

    @Test
    void isBlacklisted_callsHasKey() {
        when(redisTemplate.hasKey("token_blacklist:jti")).thenReturn(true);
        assertTrue(adapter.isBlacklisted("jti"));
    }

    @Test
    void storeUserToken_callsAdd() {
        adapter.storeUserToken(userId, "jti");
        verify(setOps).add("user_tokens:" + userId, "jti");
    }

    @Test
    void getUserTokens_callsMembers() {
        when(setOps.members("user_tokens:" + userId)).thenReturn(Set.of("jti1"));
        Set<String> tokens = adapter.getUserTokens(userId);
        assertEquals(1, tokens.size());
    }

    @Test
    void deleteUserTokens_callsDelete() {
        adapter.deleteUserTokens(userId);
        verify(redisTemplate).delete("user_tokens:" + userId);
    }
}
