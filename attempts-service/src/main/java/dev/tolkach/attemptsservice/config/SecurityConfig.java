package dev.tolkach.attemptsservice.config;

import dev.tolkach.attemptsservice.adapter.out.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
