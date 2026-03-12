package dev.tolkach.usersservice.application.port.out;

import java.util.Set;
import java.util.UUID;

public interface TokenBlacklistPort {
    void blacklistToken(String jti, long ttl);
    boolean isBlacklisted(String jti);
    void storeUserToken(UUID userId, String jti);
    Set<String> getUserTokens(UUID userId);
}
