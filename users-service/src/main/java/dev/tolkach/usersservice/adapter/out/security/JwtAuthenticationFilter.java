package dev.tolkach.usersservice.adapter.out.security;

import dev.tolkach.usersservice.adapter.in.rest.exception.ErrorResponse;
import dev.tolkach.usersservice.application.port.in.AdminUseCase;
import dev.tolkach.usersservice.application.port.out.JwtPort;
import dev.tolkach.usersservice.application.port.out.TokenBlacklistPort;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtPort jwtPort;
    private final AdminUseCase adminUseCase;
    private final TokenBlacklistPort tokenBlacklistPort;

    public JwtAuthenticationFilter(JwtPort jwtPort, AdminUseCase adminUseCase, TokenBlacklistPort tokenBlacklistPort) {
        this.jwtPort = jwtPort;
        this.adminUseCase = adminUseCase;
        this.tokenBlacklistPort = tokenBlacklistPort;
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
            Claims claims = jwtPort.extractAllClaims(jwt);
            String jti = claims.getId();

            if (tokenBlacklistPort.isBlacklisted(jti)) {
                sendErrorResponse(request, response, HttpStatus.UNAUTHORIZED, "Token revoked. Try to log in again or contact the administrator");
                return;
            }

            String email = jwtPort.extractUserName(jwt);

            if (StringUtils.hasText(email) && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = adminUseCase.getAdminByEmail(email);

                if (!userDetails.isEnabled()) {
                    sendErrorResponse(request, response, HttpStatus.FORBIDDEN,
                            "The account is inactive. Please contact the administrator");
                    return;
                }

                if (jwtPort.isTokenValid(jwt, userDetails) && userDetails.isEnabled()) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
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
