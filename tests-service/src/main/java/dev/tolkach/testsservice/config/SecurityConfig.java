package dev.tolkach.testsservice.config;

import dev.tolkach.testsservice.adapter.out.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.GET, "/api/tests/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/tests/**").permitAll()
                        .requestMatchers("/api/tests/**").authenticated()

                        .requestMatchers(HttpMethod.GET, "/api/questions/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/questions/**").permitAll()
                        .requestMatchers("/api/questions/**").authenticated()

                        .requestMatchers(HttpMethod.GET, "/api/answer-options/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/answer-options/**").permitAll()
                        .requestMatchers("/api/answer-options/**").authenticated()

                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            sendJsonError(request, response, HttpStatus.UNAUTHORIZED,
                                    "Unauthorized",
                                    "A valid JWT token is required");
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            sendJsonError(request, response, HttpStatus.FORBIDDEN,
                                    "Forbidden",
                                    "Insufficient rights or account is inactive");
                        })
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private void sendJsonError(HttpServletRequest request, HttpServletResponse response, HttpStatus status, String error, String message)
            throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("message", message);
        errorBody.put("path", request.getRequestURI());
        errorBody.put("timestamp", LocalDateTime.now().toString());
        errorBody.put("status", status.value());
        errorBody.put("error", status.getReasonPhrase());

        new ObjectMapper().writeValue(response.getWriter(), errorBody);
    }
}
