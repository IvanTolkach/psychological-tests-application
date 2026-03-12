package dev.tolkach.testsservice.application.port.out;

public interface TokenBlacklistPort {
    boolean isBlacklisted(String jti);
}
