package dev.tolkach.methodologiesservice.application.port.out;

public interface TokenBlacklistPort {
    boolean isBlacklisted(String jti);
}
