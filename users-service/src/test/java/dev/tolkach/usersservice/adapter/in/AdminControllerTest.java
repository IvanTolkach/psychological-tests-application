package dev.tolkach.usersservice.adapter.in;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import dev.tolkach.usersservice.adapter.in.rest.AdminController;
import dev.tolkach.usersservice.adapter.in.rest.dto.AdminDto;
import dev.tolkach.usersservice.adapter.in.rest.dto.PasswordChangeDto;
import dev.tolkach.usersservice.adapter.in.rest.mapper.AdminDtoMapper;
import dev.tolkach.usersservice.adapter.in.rest.mapper.PasswordChangeDtoMapper;
import dev.tolkach.usersservice.application.model.Admin;
import dev.tolkach.usersservice.application.model.AdminRole;
import dev.tolkach.usersservice.application.model.PasswordChange;
import dev.tolkach.usersservice.application.port.in.AdminUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private AdminUseCase adminUseCase;

    @Mock
    private AdminDtoMapper adminDtoMapper;

    @Mock
    private PasswordChangeDtoMapper passwordChangeDtoMapper;

    @InjectMocks
    private AdminController controller;

    private Admin admin;
    private AdminDto adminDto;
    private UUID adminId;

    @BeforeEach
    void setup() {
        adminId = UUID.randomUUID();
        admin = new Admin();
        admin.setId(adminId);
        admin.setEmail("test@example.com");

        adminDto = new AdminDto();
        adminDto.setId(adminId);
        adminDto.setEmail("test@example.com");
    }

    @Test
    void getAdmins_returnsDtoList() {
        when(adminDtoMapper.toEntity(any())).thenReturn(admin);
        when(adminUseCase.getAdminsByFilter(admin)).thenReturn(List.of(admin));
        when(adminDtoMapper.toDto(admin)).thenReturn(adminDto);

        ResponseEntity<List<AdminDto>> response = controller.getAdmins(new AdminDto());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(adminDto, response.getBody().getFirst());
    }

    @Test
    void getAdminById_returnsDto() {
        when(adminUseCase.getAdminById(adminId)).thenReturn(admin);
        when(adminDtoMapper.toDto(admin)).thenReturn(adminDto);

        ResponseEntity<AdminDto> response = controller.getAdminById(adminId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(adminDto, response.getBody());
    }

    @Test
    void createUpdateAdmin_createsNew_returnsCreated() {
        AdminDto newDto = new AdminDto();
        when(adminDtoMapper.toEntity(newDto)).thenReturn(admin);
        when(adminUseCase.createUpdateAdmin(admin)).thenReturn(admin);
        when(adminDtoMapper.toDto(admin)).thenReturn(adminDto);

        ResponseEntity<AdminDto> response = controller.createUpdateAdmin(newDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(adminDto, response.getBody());
    }

    @Test
    void createUpdateAdmin_updatesExisting_returnsOk() {
        AdminDto updateDto = new AdminDto();
        updateDto.setId(adminId);
        when(adminDtoMapper.toEntity(updateDto)).thenReturn(admin);
        when(adminUseCase.createUpdateAdmin(admin)).thenReturn(admin);
        when(adminDtoMapper.toDto(admin)).thenReturn(adminDto);

        ResponseEntity<AdminDto> response = controller.createUpdateAdmin(updateDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(adminDto, response.getBody());
    }

    @Test
    void deactivateAdmin_returnsNoContent() {
        ResponseEntity<Void> response = controller.deactivateAdmin(adminId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(adminUseCase).deactivateAdmin(adminId);
    }

    @Test
    void activateAdmin_returnsNoContent() {
        ResponseEntity<Void> response = controller.activateAdmin(adminId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(adminUseCase).activateAdmin(adminId);
    }

    @Test
    void changePassword_returnsNoContent() {
        PasswordChangeDto dto = new PasswordChangeDto();
        PasswordChange entity = new PasswordChange();
        when(passwordChangeDtoMapper.toEntity(dto)).thenReturn(entity);

        ResponseEntity<Void> response = controller.changePassword(dto);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(adminUseCase).changePassword(entity);
    }

    @Test
    void changeRole_returnsNoContent() {
        ResponseEntity<Void> response = controller.changeRole(adminId, AdminRole.SUPER);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(adminUseCase).changeRole(adminId, AdminRole.SUPER);
    }

    @Test
    void getCurrentAdmin_returnsDto() {
        when(adminUseCase.getCuttentAdmin()).thenReturn(admin);
        when(adminDtoMapper.toDto(admin)).thenReturn(adminDto);

        ResponseEntity<AdminDto> response = controller.getCurrentAdmin();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(adminDto, response.getBody());
    }
}