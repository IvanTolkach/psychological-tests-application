package dev.tolkach.usersservice.application.service;

import dev.tolkach.usersservice.application.model.Admin;
import dev.tolkach.usersservice.application.model.JwtResponse;
import dev.tolkach.usersservice.application.port.in.AdminUseCase;
import dev.tolkach.usersservice.application.port.in.AuthUseCase;
import dev.tolkach.usersservice.application.port.out.JwtPort;
import dev.tolkach.usersservice.application.port.out.TokenBlacklistPort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;

public class AuthService implements AuthUseCase {

    private final AdminUseCase adminUseCase;
    private final JwtPort jwtPort;
    private final AuthenticationManager authenticationManager;
    private final TokenBlacklistPort tokenBlacklistPort;

    public AuthService(AdminUseCase adminUseCase, JwtPort jwtPort, AuthenticationManager authenticationManager, TokenBlacklistPort tokenBlacklistPort) {
        this.adminUseCase = adminUseCase;
        this.jwtPort = jwtPort;
        this.authenticationManager = authenticationManager;
        this.tokenBlacklistPort = tokenBlacklistPort;
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
        String jti = jwtPort.extractJti(jwt);
        tokenBlacklistPort.storeUserToken(admin.getId(), jti);

        return JwtResponse.builder().token(jwt).build();
    }
}
