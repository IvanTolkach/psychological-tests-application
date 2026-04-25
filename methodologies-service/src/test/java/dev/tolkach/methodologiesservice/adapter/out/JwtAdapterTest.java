package dev.tolkach.methodologiesservice.adapter.out;

import dev.tolkach.methodologiesservice.adapter.out.security.JwtAdapter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAdapterTest {

    JwtAdapter adapter;
    String signingKey;

    @BeforeEach
    void setUp() {
        adapter = new JwtAdapter();
        signingKey = Base64.getEncoder()
                .encodeToString("testkey1234567890testkey1234567890".getBytes());
        ReflectionTestUtils.setField(adapter, "jwtSigningKey", signingKey);
    }

    private String generateToken(Instant expiration) {
        return Jwts.builder()
                .subject("user1")
                .expiration(Date.from(expiration))
                .signWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(signingKey)))
                .compact();
    }

    private Instant now() {
        return Instant.now();
    }

    @Test
    void extractUserName_returnsSubject() {
        String token = generateToken(now().plusSeconds(600));

        assertEquals("user1", adapter.extractUserName(token));
    }

    @Test
    void isTokenValid_validToken_true() {
        String token = generateToken(now().plusSeconds(600));

        assertTrue(adapter.isTokenValid(token));
    }

    @Test
    void isTokenValid_expiredToken_false() {
        String token = generateToken(now().minusSeconds(60));

        assertFalse(adapter.isTokenValid(token));
    }

    @Test
    void isTokenValid_invalidToken_false() {
        String invalidToken = "invalid.token.value";

        assertFalse(adapter.isTokenValid(invalidToken));
    }

    @Test
    void isTokenValid_userDetails_matches_true() {
        String token = generateToken(now().plusSeconds(600));

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("user1");

        assertTrue(adapter.isTokenValid(token, userDetails));
    }

    @Test
    void isTokenValid_userDetails_mismatch_false() {
        String token = generateToken(now().plusSeconds(600));

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("otherUser");

        assertFalse(adapter.isTokenValid(token, userDetails));
    }

    @Test
    void isTokenValid_userDetails_null_callsIsTokenValid() {
        String token = generateToken(now().plusSeconds(600));

        assertTrue(adapter.isTokenValid(token, null));
    }
}
