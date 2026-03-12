package dev.tolkach.attemptsservice.application.port.out;

public interface TokenBlacklistPort {
    boolean isBlacklisted(String jti);
}
