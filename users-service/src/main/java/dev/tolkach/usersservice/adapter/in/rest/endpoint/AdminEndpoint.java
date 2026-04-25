package dev.tolkach.usersservice.adapter.in.rest.endpoint;

import dev.tolkach.usersservice.adapter.in.rest.dto.AdminDto;
import dev.tolkach.usersservice.adapter.in.rest.dto.PasswordChangeDto;
import dev.tolkach.usersservice.application.model.AdminRole;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public interface AdminEndpoint {
    @PostMapping(ApiEndpoints.Admin.SEARCH)
    @PreAuthorize("hasAnyAuthority('ROLE_STANDARD', 'ROLE_SUPER')")
    ResponseEntity<List<AdminDto>> getAdmins(@RequestBody AdminDto filter);

    @GetMapping(ApiEndpoints.Admin.BY_ID)
    @PreAuthorize("hasAnyAuthority('ROLE_STANDARD', 'ROLE_SUPER')")
    ResponseEntity<AdminDto> getAdminById(@PathVariable UUID adminId);

    @PostMapping(ApiEndpoints.Admin.BASE)
    @PreAuthorize("hasAnyAuthority('ROLE_STANDARD', 'ROLE_SUPER')")
    ResponseEntity<AdminDto> createUpdateAdmin(@Valid @RequestBody AdminDto dto);

    @DeleteMapping(ApiEndpoints.Admin.BY_ID)
    @PreAuthorize("hasAuthority('ROLE_SUPER')")
    ResponseEntity<Void> deactivateAdmin(@PathVariable UUID adminId);

    @PatchMapping(ApiEndpoints.Admin.ACTIVATE)
    @PreAuthorize("hasAuthority('ROLE_SUPER')")
    ResponseEntity<Void> activateAdmin(@PathVariable UUID adminId);

    @PatchMapping(ApiEndpoints.Admin.CHANGE_PASSWORD)
    @PreAuthorize("authentication.principal.id == #passwordChangeDto.adminId or hasAuthority('ROLE_SUPER')")
    ResponseEntity<Void> changePassword(@Valid @RequestBody PasswordChangeDto passwordChangeDto);

    @PatchMapping(ApiEndpoints.Admin.CHANGE_ROLE)
    @PreAuthorize("hasAuthority('ROLE_SUPER')")
    ResponseEntity<Void> changeRole(@PathVariable UUID adminId, @RequestParam AdminRole adminRole);

    @GetMapping(ApiEndpoints.Admin.CURRENT)
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<AdminDto> getCurrentAdmin();
}
