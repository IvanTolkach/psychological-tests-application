package dev.tolkach.attemptsservice.config;

import dev.tolkach.attemptsservice.adapter.out.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
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
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.GET, "/api/test-attempts/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/test-attempts/**").permitAll()
                        .requestMatchers("/api/test-attempts/**").authenticated()

                        .requestMatchers(HttpMethod.GET, "/api/student-answers/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/student-answers/**").permitAll()
                        .requestMatchers("/api/student-answers/**").authenticated()

                        .requestMatchers(HttpMethod.GET, "/api/test-attempt-scores/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/test-attempt-scores/**").permitAll()
                        .requestMatchers("/api/test-attempt-scores/**").authenticated()

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

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
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
