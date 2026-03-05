package dev.tolkach.testsservice.adapter.out.security;

import dev.tolkach.testsservice.application.port.out.JwtPort;
import dev.tolkach.testsservice.adapter.in.rest.exception.ErrorResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtPort jwtPort;

    public JwtAuthenticationFilter(JwtPort jwtPort) {
        this.jwtPort = jwtPort;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);

        try {
            String email = jwtPort.extractUserName(jwt);

            if (StringUtils.hasText(email) && SecurityContextHolder.getContext().getAuthentication() == null) {

                if (jwtPort.isTokenValid(jwt)) {

                    Claims claims = jwtPort.extractAllClaims(jwt);
                    List<GrantedAuthority> authorities = extractAuthoritiesFromToken(jwt);

                    String adminIdStr = claims.get("id", String.class);
                    UUID adminId = adminIdStr != null ? UUID.fromString(adminIdStr) : null;

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    email,
                                    null,
                                    authorities
                            );

                    WebAuthenticationDetails webDetails = new WebAuthenticationDetailsSource().buildDetails(request);

                    Map<String, Object> customDetails = new HashMap<>();
                    customDetails.put("adminId", adminId);
                    customDetails.put("claims", claims);

                    Map<String, Object> combinedDetails = new LinkedHashMap<>();
                    combinedDetails.put("web", webDetails);
                    combinedDetails.putAll(customDetails);

                    authToken.setDetails(combinedDetails);
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

        } catch (ExpiredJwtException e) {
            sendErrorResponse(request, response, HttpStatus.UNAUTHORIZED, "Your token has expired. Please log in again");
            return;

        } catch (MalformedJwtException | UnsupportedJwtException | SignatureException | IllegalArgumentException e) {
            sendErrorResponse(request, response, HttpStatus.UNAUTHORIZED, "Invalid or corrupted token");
            return;

        } catch (Exception e) {
            sendErrorResponse(request, response, HttpStatus.UNAUTHORIZED, "Authentication error: " + e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }

    private List<GrantedAuthority> extractAuthoritiesFromToken(String jwt) {
        Claims claims = jwtPort.extractAllClaims(jwt);

        String role = claims.get("role", String.class);

        if (role == null) {
            return Collections.emptyList();
        }

        return List.of(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
    }

    private void sendErrorResponse(HttpServletRequest request, HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorResponse error = new ErrorResponse(
                status,
                message,
                request.getRequestURI()
        );

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), error);
    }
}
