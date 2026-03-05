package dev.tolkach.methodologiesservice.config;

import dev.tolkach.methodologiesservice.adapter.out.security.JwtAuthenticationFilter;
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
                        .requestMatchers(HttpMethod.GET, "/api/methodologies/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/methodologies/**").permitAll()
                        .requestMatchers("/api/methodologies/**").authenticated()

                        .requestMatchers(HttpMethod.GET, "/api/scales/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/scales/**").permitAll()
                        .requestMatchers("/api/scales/**").authenticated()

                        .requestMatchers(HttpMethod.GET, "/api/score-ranges/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/score-ranges/**").permitAll()
                        .requestMatchers("/api/score-ranges/**").authenticated()

                        .requestMatchers(HttpMethod.GET, "/api/scale-questions/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/scale-questions/**").permitAll()
                        .requestMatchers("/api/scale-questions/**").authenticated()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
