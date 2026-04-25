package dev.tolkach.attemptsservice.application.port.out;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtPort {
    String extractUserName(String token);
    boolean isTokenValid(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
    Claims extractAllClaims(String token);
}
