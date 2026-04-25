package dev.tolkach.usersservice.application.port.out;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtPort {
    String generateToken(UserDetails userDetails);
    String extractUserName(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
    String extractJti(String token);
    Claims extractAllClaims(String token);
}
