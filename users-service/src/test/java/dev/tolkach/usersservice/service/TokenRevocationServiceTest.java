package dev.tolkach.usersservice.service;

import dev.tolkach.usersservice.application.port.out.TokenBlacklistPort;
import dev.tolkach.usersservice.application.service.TokenRevocationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TokenRevocationServiceTest {

    @Mock
    TokenBlacklistPort tokenBlacklistPort;

    @InjectMocks
    TokenRevocationService service;

    UUID userId = UUID.randomUUID();

    @Test
    void revokeTokens_nullTokens() {

        when(tokenBlacklistPort.getUserTokens(userId))
                .thenReturn(null);

        service.revokeUserTokens(userId);

        verify(tokenBlacklistPort, never())
                .blacklistToken(any(), anyLong());
    }

    @Test
    void revokeTokens_emptyTokens() {

        when(tokenBlacklistPort.getUserTokens(userId))
                .thenReturn(Set.of());

        service.revokeUserTokens(userId);

        verify(tokenBlacklistPort, never())
                .blacklistToken(any(), anyLong());
    }

    @Test
    void revokeTokens_success() {

        Set<String> tokens = Set.of("jti1", "jti2");

        when(tokenBlacklistPort.getUserTokens(userId))
                .thenReturn(tokens);

        service.revokeUserTokens(userId);

        verify(tokenBlacklistPort, times(2))
                .blacklistToken(any(), eq(60 * 60 * 12L));

        verify(tokenBlacklistPort)
                .deleteUserTokens(userId);
    }
}
