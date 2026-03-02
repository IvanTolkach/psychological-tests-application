package dev.tolkach.methodologiesservice.adapter.in.rest.endpoint;

import dev.tolkach.methodologiesservice.adapter.in.rest.dto.MethodologyDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public interface MethodologyEndpoint {
    @PostMapping(ApiEndpoints.Methodology.SEARCH)
    ResponseEntity<List<MethodologyDto>> getMethodologies(@RequestBody MethodologyDto filter);

    @GetMapping(ApiEndpoints.Methodology.BY_ID)
    ResponseEntity<MethodologyDto> getMethodologyById(@PathVariable UUID methodologyId);

    @PostMapping(ApiEndpoints.Methodology.BASE)
    ResponseEntity<MethodologyDto> createUpdateMethodology(@Valid @RequestBody MethodologyDto dto);

    @DeleteMapping(ApiEndpoints.Methodology.BY_ID)
    ResponseEntity<Void> deleteMethodology(@PathVariable UUID methodologyId);
}
