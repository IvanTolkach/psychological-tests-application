package dev.tolkach.usersservice.adapter.in.rest;

import dev.tolkach.usersservice.adapter.in.rest.dto.AdminDto;
import dev.tolkach.usersservice.adapter.in.rest.dto.JwtResponseDto;
import dev.tolkach.usersservice.adapter.in.rest.dto.SignInDto;
import dev.tolkach.usersservice.adapter.in.rest.endpoint.AuthEndpoint;
import dev.tolkach.usersservice.adapter.in.rest.mapper.AdminDtoMapper;
import dev.tolkach.usersservice.adapter.in.rest.mapper.JwtResponseDtoMapper;
import dev.tolkach.usersservice.application.model.Admin;
import dev.tolkach.usersservice.application.model.JwtResponse;
import dev.tolkach.usersservice.application.port.in.AdminUseCase;
import dev.tolkach.usersservice.application.port.in.AuthUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthEndpoint {

    private final AuthUseCase authUseCase;
    private final AdminUseCase adminUseCase;
    private final AdminDtoMapper adminDtoMapper;
    private final JwtResponseDtoMapper jwtResponseDtoMapper;

    public AuthController(AuthUseCase authUseCase, AdminUseCase adminUseCase, AdminDtoMapper adminDtoMapper, JwtResponseDtoMapper jwtResponseDtoMapper) {
        this.authUseCase = authUseCase;
        this.adminUseCase = adminUseCase;
        this.adminDtoMapper = adminDtoMapper;
        this.jwtResponseDtoMapper = jwtResponseDtoMapper;
    }

    @Override
    public ResponseEntity<JwtResponseDto> signIn(@Valid @RequestBody SignInDto signInDto) {
        JwtResponse response = authUseCase.signIn(signInDto.getEmail(), signInDto.getPassword());
        JwtResponseDto responseDto = jwtResponseDtoMapper.toDto(response);
        return ResponseEntity.ok(responseDto);
    }

    @Override
    public ResponseEntity<AdminDto> signUp(@Valid @RequestBody AdminDto adminDto) {
        adminDto.setId(null);
        Admin admin = adminDtoMapper.toEntity(adminDto);
        Admin created = adminUseCase.createUpdateAdmin(admin);
        AdminDto responseDto = adminDtoMapper.toDto(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
