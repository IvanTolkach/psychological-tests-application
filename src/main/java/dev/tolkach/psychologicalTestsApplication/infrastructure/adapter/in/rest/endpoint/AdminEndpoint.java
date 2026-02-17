package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.endpoint;

import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto.AdminDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public interface AdminEndpoint {
    @PostMapping(ApiEndpoints.Admin.SEARCH)
    ResponseEntity<List<AdminDto>> getAdmins(@RequestBody AdminDto filter);

    @PostMapping(ApiEndpoints.Admin.BASE)
    ResponseEntity<AdminDto> createUpdateAdmin(@Valid @RequestBody AdminDto dto);

    @DeleteMapping(ApiEndpoints.Admin.BY_ID)
    ResponseEntity<AdminDto> deactivateAdmin(@PathVariable UUID adminId);

    @PatchMapping(ApiEndpoints.Admin.CHANGE_PASSWORD)
    ResponseEntity<Void> changePassword(@PathVariable UUID adminId, @RequestParam String oldPassword, @RequestParam String newPassword);
}
