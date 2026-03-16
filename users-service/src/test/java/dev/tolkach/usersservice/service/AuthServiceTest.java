package dev.tolkach.usersservice.service;

import dev.tolkach.usersservice.application.model.Admin;
import dev.tolkach.usersservice.application.model.JwtResponse;
import dev.tolkach.usersservice.application.port.in.AdminUseCase;
import dev.tolkach.usersservice.application.port.out.JwtPort;
import dev.tolkach.usersservice.application.port.out.TokenBlacklistPort;
import dev.tolkach.usersservice.application.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    AdminUseCase adminUseCase;

    @Mock
    JwtPort jwtPort;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    TokenBlacklistPort tokenBlacklistPort;

    @InjectMocks
    AuthService service;

    Admin admin;

    @BeforeEach
    void setup() {
        admin = new Admin();
        admin.setId(UUID.randomUUID());
        admin.setEmail("admin@mail.com");
        admin.setIsActive(true);
    }

    @Test
    void signIn_authenticationFailed() {

        doThrow(new RuntimeException())
                .when(authenticationManager)
                .authenticate(any());

        assertThrows(AccessDeniedException.class,
                () -> service.signIn("mail", "pass"));
    }

    @Test
    void signIn_adminNotActive() {

        admin.setIsActive(false);

        when(adminUseCase.getAdminByEmail(admin.getEmail()))
                .thenReturn(admin);

        assertThrows(IllegalArgumentException.class,
                () -> service.signIn(admin.getEmail(), "pass"));
    }

    @Test
    void signIn_success() {

        String token = "jwtToken";
        String jti = "jti123";

        when(adminUseCase.getAdminByEmail(admin.getEmail()))
                .thenReturn(admin);

        when(jwtPort.generateToken(admin))
                .thenReturn(token);

        when(jwtPort.extractJti(token))
                .thenReturn(jti);

        JwtResponse response = service.signIn(admin.getEmail(), "pass");

        assertEquals(token, response.getToken());

        verify(tokenBlacklistPort)
                .storeUserToken(admin.getId(), jti);
    }
}