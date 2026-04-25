package dev.tolkach.usersservice.adapter.in;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import dev.tolkach.usersservice.adapter.in.rest.AuthController;
import dev.tolkach.usersservice.adapter.in.rest.dto.AdminDto;
import dev.tolkach.usersservice.adapter.in.rest.dto.JwtResponseDto;
import dev.tolkach.usersservice.adapter.in.rest.dto.SignInDto;
import dev.tolkach.usersservice.adapter.in.rest.mapper.AdminDtoMapper;
import dev.tolkach.usersservice.adapter.in.rest.mapper.JwtResponseDtoMapper;
import dev.tolkach.usersservice.application.model.Admin;
import dev.tolkach.usersservice.application.model.JwtResponse;
import dev.tolkach.usersservice.application.port.in.AdminUseCase;
import dev.tolkach.usersservice.application.port.in.AuthUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    AuthUseCase authUseCase;

    @Mock
    AdminUseCase adminUseCase;

    @Mock
    AdminDtoMapper adminDtoMapper;

    @Mock
    JwtResponseDtoMapper jwtResponseDtoMapper;

    @InjectMocks
    AuthController controller;

    private Admin admin;
    private AdminDto adminDto;
    private JwtResponse jwtResponse;
    private JwtResponseDto jwtResponseDto;

    @BeforeEach
    void setup() {
        admin = new Admin();
        admin.setId(UUID.randomUUID());
        admin.setEmail("test@example.com");

        adminDto = new AdminDto();
        adminDto.setId(admin.getId());
        adminDto.setEmail(admin.getEmail());

        jwtResponse = new JwtResponse("token");
        jwtResponseDto = new JwtResponseDto("token");
    }

    @Test
    void signIn_returnsJwtResponseDto() {
        SignInDto dto = new SignInDto();
        dto.setEmail("test@example.com");
        dto.setPassword("password");

        when(authUseCase.signIn(dto.getEmail(), dto.getPassword())).thenReturn(jwtResponse);
        when(jwtResponseDtoMapper.toDto(jwtResponse)).thenReturn(jwtResponseDto);

        ResponseEntity<JwtResponseDto> response = controller.signIn(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(jwtResponseDto, response.getBody());
    }

    @Test
    void signUp_createsAdmin_returnsCreated() {
        AdminDto inputDto = new AdminDto();
        inputDto.setEmail("new@example.com");
        inputDto.setId(null);

        when(adminDtoMapper.toEntity(inputDto)).thenReturn(admin);
        when(adminUseCase.createUpdateAdmin(admin)).thenReturn(admin);
        when(adminDtoMapper.toDto(admin)).thenReturn(adminDto);

        ResponseEntity<AdminDto> response = controller.signUp(inputDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(adminDto, response.getBody());
    }
}
