package dev.tolkach.usersservice.adapter.in.rest.endpoint;

import dev.tolkach.usersservice.adapter.in.rest.dto.AdminDto;
import dev.tolkach.usersservice.adapter.in.rest.dto.PasswordChangeDto;
import dev.tolkach.usersservice.application.model.AdminRole;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public interface AdminEndpoint {
    @PostMapping(ApiEndpoints.Admin.SEARCH)
    ResponseEntity<List<AdminDto>> getAdmins(@RequestBody AdminDto filter);

    @GetMapping(ApiEndpoints.Admin.BY_ID)
    ResponseEntity<AdminDto> getAdminById(@PathVariable UUID adminId);

    @PostMapping(ApiEndpoints.Admin.BASE)
    ResponseEntity<AdminDto> createUpdateAdmin(@Valid @RequestBody AdminDto dto);

    @DeleteMapping(ApiEndpoints.Admin.BY_ID)
    ResponseEntity<Void> deactivateAdmin(@PathVariable UUID adminId);

    @PatchMapping(ApiEndpoints.Admin.ACTIVATE)
    ResponseEntity<Void> activateAdmin(@PathVariable UUID adminId);

    @PatchMapping(ApiEndpoints.Admin.CHANGE_PASSWORD)
    ResponseEntity<Void> changePassword(@Valid @RequestBody PasswordChangeDto passwordChangeDto);

    @PatchMapping(ApiEndpoints.Admin.CHANGE_ROLE)
    ResponseEntity<Void> changeRole(@PathVariable UUID adminId, @RequestParam AdminRole adminRole);

    @GetMapping(ApiEndpoints.Admin.BASE)
    ResponseEntity<AdminDto> getCurrentAdmin();
}
