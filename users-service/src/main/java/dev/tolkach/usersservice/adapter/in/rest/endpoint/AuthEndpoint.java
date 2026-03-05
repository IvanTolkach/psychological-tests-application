package dev.tolkach.usersservice.adapter.in.rest.endpoint;

import dev.tolkach.usersservice.adapter.in.rest.dto.AdminDto;
import dev.tolkach.usersservice.adapter.in.rest.dto.JwtResponseDto;
import dev.tolkach.usersservice.adapter.in.rest.dto.SignInDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;

public interface AuthEndpoint {
    @PostMapping(ApiEndpoints.Authentication.SIGN_IN)
    @PreAuthorize("permitAll()")
    ResponseEntity<JwtResponseDto> signIn(SignInDto signInDto);

    @PostMapping(ApiEndpoints.Authentication.SIGN_UP)
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<AdminDto> signUp(AdminDto adminDto);
}
