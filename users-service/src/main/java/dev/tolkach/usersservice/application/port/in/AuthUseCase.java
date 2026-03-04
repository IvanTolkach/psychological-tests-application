package dev.tolkach.usersservice.application.port.in;

import dev.tolkach.usersservice.application.model.JwtResponse;

public interface AuthUseCase {
    JwtResponse signIn(String email, String password);
}
