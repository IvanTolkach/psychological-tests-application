package dev.tolkach.usersservice.application.port.out;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtPort {
    String generateToken(UserDetails userDetails);
    String extractUserName(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
}
