package dev.tolkach.usersservice.adapter.in.rest;

import dev.tolkach.usersservice.adapter.in.rest.dto.AdminDto;
import dev.tolkach.usersservice.adapter.in.rest.dto.PasswordChangeDto;
import dev.tolkach.usersservice.adapter.in.rest.endpoint.AdminEndpoint;
import dev.tolkach.usersservice.adapter.in.rest.mapper.AdminDtoMapper;
import dev.tolkach.usersservice.adapter.in.rest.mapper.PasswordChangeDtoMapper;
import dev.tolkach.usersservice.application.model.Admin;
import dev.tolkach.usersservice.application.model.AdminRole;
import dev.tolkach.usersservice.application.model.PasswordChange;
import dev.tolkach.usersservice.application.port.in.AdminUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class AdminController implements AdminEndpoint {

    private final AdminUseCase adminUseCase;
    private final AdminDtoMapper adminDtoMapper;
    private final PasswordChangeDtoMapper passwordChangeDtoMapper;

    public AdminController(AdminUseCase adminUseCase, AdminDtoMapper adminDtoMapper, PasswordChangeDtoMapper passwordChangeDtoMapper) {
        this.adminUseCase = adminUseCase;
        this.adminDtoMapper = adminDtoMapper;
        this.passwordChangeDtoMapper = passwordChangeDtoMapper;
    }

    @Override
    public ResponseEntity<List<AdminDto>> getAdmins(AdminDto filter) {
        Admin adminFilter = adminDtoMapper.toEntity(filter);
        List<Admin> admins = adminUseCase.getAdminsByFilter(adminFilter);
        List<AdminDto> responseDtos = admins.stream().map(adminDtoMapper::toDto).toList();
        return ResponseEntity.ok(responseDtos);
    }

    @Override
    public ResponseEntity<AdminDto> getAdminById(UUID adminId) {
        Admin admin = adminUseCase.getAdminById(adminId);
        AdminDto responseDto = adminDtoMapper.toDto(admin);
        return ResponseEntity.ok(responseDto);
    }

    @Override
    public ResponseEntity<AdminDto> createUpdateAdmin(AdminDto dto) {
        Admin admin = adminDtoMapper.toEntity(dto);
        Admin saved = adminUseCase.createUpdateAdmin(admin);
        AdminDto responseDto = adminDtoMapper.toDto(saved);
        if (dto.getId() == null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } else {
            return ResponseEntity.ok(responseDto);
        }
    }

    @Override
    public ResponseEntity<Void> deactivateAdmin(UUID adminId) {
        adminUseCase.deactivateAdmin(adminId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Void> activateAdmin(UUID adminId) {
        adminUseCase.activateAdmin(adminId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Void> changePassword(PasswordChangeDto passwordChangeDto) {
        PasswordChange passwordChange = passwordChangeDtoMapper.toEntity(passwordChangeDto);
        adminUseCase.changePassword(passwordChange);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Void> changeRole(UUID adminId, AdminRole adminRole) {
        adminUseCase.changeRole(adminId, adminRole);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<AdminDto> getCurrentAdmin() {
        Admin current = adminUseCase.getCuttentAdmin();
        AdminDto responseDto = adminDtoMapper.toDto(current);
        return ResponseEntity.ok(responseDto);
    }
}
