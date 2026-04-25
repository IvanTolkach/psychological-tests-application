package dev.tolkach.usersservice.adapter.out;

import dev.tolkach.usersservice.adapter.out.security.JwtAdapter;
import dev.tolkach.usersservice.application.model.Admin;
import dev.tolkach.usersservice.application.model.AdminRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class JwtAdapterTest {

    @InjectMocks
    JwtAdapter adapter;

    Admin admin;

    @BeforeEach
    void setup() throws Exception {
        Field field = JwtAdapter.class.getDeclaredField("jwtSigningKey");
        field.setAccessible(true);
        field.set(adapter, Base64.getEncoder()
                .encodeToString("01234567890123456789012345678901".getBytes()));

        admin = new Admin();
        admin.setId(UUID.randomUUID());
        admin.setEmail("admin@example.com");
        admin.setRole(AdminRole.SUPER);
        admin.setIsActive(true);
    }

    @Test
    void generateToken_andExtractClaims() {
        String token = adapter.generateToken(admin);

        String username = adapter.extractUserName(token);
        assertEquals(admin.getEmail(), username);

        String jti = adapter.extractJti(token);
        assertNotNull(jti);

        assertTrue(adapter.isTokenValid(token, admin));
    }

    @Test
    void generateToken_invalidUserDetails() {
        UserDetails user = mock(UserDetails.class);
        assertThrows(IllegalArgumentException.class,
                () -> adapter.generateToken(user));
    }
}
