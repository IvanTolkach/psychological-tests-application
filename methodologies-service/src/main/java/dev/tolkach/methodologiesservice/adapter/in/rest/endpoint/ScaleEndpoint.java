package dev.tolkach.methodologiesservice.adapter.in.rest.endpoint;

import dev.tolkach.methodologiesservice.adapter.in.rest.dto.ScaleDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public interface ScaleEndpoint {
    @PostMapping(ApiEndpoints.Scale.SEARCH)
    @PreAuthorize("permitAll()")
    ResponseEntity<List<ScaleDto>> getScales(@RequestBody ScaleDto filter);

    @GetMapping(ApiEndpoints.Scale.BY_ID)
    @PreAuthorize("permitAll()")
    ResponseEntity<ScaleDto> getScaleById(@PathVariable UUID scaleId);

    @PostMapping(ApiEndpoints.Scale.BASE)
    @PreAuthorize("hasAuthority('ROLE_SUPER')")
    ResponseEntity<ScaleDto> createUpdateScale(@Valid @RequestBody ScaleDto dto);

    @DeleteMapping(ApiEndpoints.Scale.BY_ID)
    @PreAuthorize("hasAuthority('ROLE_SUPER')")
    ResponseEntity<Void> deleteScale(@PathVariable UUID scaleId);
}
