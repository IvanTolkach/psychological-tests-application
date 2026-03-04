package dev.tolkach.usersservice.adapter.in.rest.endpoint;

import dev.tolkach.usersservice.adapter.in.rest.dto.AdminDto;
import dev.tolkach.usersservice.adapter.in.rest.dto.JwtResponseDto;
import dev.tolkach.usersservice.adapter.in.rest.dto.SignInDto;
import org.springframework.http.ResponseEntity;

public interface AuthEndpoint {
    ResponseEntity<JwtResponseDto> signIn(SignInDto signInDto);
    ResponseEntity<AdminDto> signUp(AdminDto adminDto);
}
