package dev.tolkach.usersservice.application.service;

import dev.tolkach.usersservice.application.port.out.TokenBlacklistPort;

import java.util.Set;
import java.util.UUID;

public class TokenRevocationService {

    private final TokenBlacklistPort tokenBlacklistPort;

    public TokenRevocationService(TokenBlacklistPort tokenBlacklistPort) {
        this.tokenBlacklistPort = tokenBlacklistPort;
    }

    public void revokeUserTokens(UUID userId) {

        Set<String> tokens = tokenBlacklistPort.getUserTokens(userId);

        if (tokens == null || tokens.isEmpty()) {
            return;
        }

        for (String jti : tokens) {

            long ttlSeconds = 60 * 60 * 12;

            tokenBlacklistPort.blacklistToken(jti, ttlSeconds);
        }
    }
}
