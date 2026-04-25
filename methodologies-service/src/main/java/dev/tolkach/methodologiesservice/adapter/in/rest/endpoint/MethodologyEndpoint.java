package dev.tolkach.methodologiesservice.adapter.in.rest.endpoint;

import dev.tolkach.methodologiesservice.adapter.in.rest.dto.MethodologyDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public interface MethodologyEndpoint {
    @PostMapping(ApiEndpoints.Methodology.SEARCH)
    @PreAuthorize("permitAll()")
    ResponseEntity<List<MethodologyDto>> getMethodologies(@RequestBody MethodologyDto filter);

    @GetMapping(ApiEndpoints.Methodology.BY_ID)
    @PreAuthorize("permitAll()")
    ResponseEntity<MethodologyDto> getMethodologyById(@PathVariable UUID methodologyId);

    @PostMapping(ApiEndpoints.Methodology.BASE)
    @PreAuthorize("hasAuthority('ROLE_SUPER')")
    ResponseEntity<MethodologyDto> createUpdateMethodology(@Valid @RequestBody MethodologyDto dto);

    @DeleteMapping(ApiEndpoints.Methodology.BY_ID)
    @PreAuthorize("hasAuthority('ROLE_SUPER')")
    ResponseEntity<Void> deleteMethodology(@PathVariable UUID methodologyId);
}
