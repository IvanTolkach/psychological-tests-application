package dev.tolkach.usersservice.application.service;

import dev.tolkach.usersservice.application.model.Admin;
import dev.tolkach.usersservice.application.model.JwtResponse;
import dev.tolkach.usersservice.application.port.in.AdminUseCase;
import dev.tolkach.usersservice.application.port.in.AuthUseCase;
import dev.tolkach.usersservice.application.port.out.JwtPort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;

public class AuthService implements AuthUseCase {

    private final AdminUseCase adminUseCase;
    private final JwtPort jwtPort;
    private final AuthenticationManager authenticationManager;

    public AuthService(AdminUseCase adminUseCase, JwtPort jwtPort, AuthenticationManager authenticationManager) {
        this.adminUseCase = adminUseCase;
        this.jwtPort = jwtPort;
        this.authenticationManager = authenticationManager;
    }

    @Override
    @Transactional(readOnly = true)
    public JwtResponse signIn(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        Admin admin = adminUseCase.getAdminByEmail(email);
        if (!admin.getIsActive()) {
            throw new IllegalArgumentException("Account is not active");
        }

        String jwt = jwtPort.generateToken(admin);
        return JwtResponse.builder().token(jwt).build();
    }
}
