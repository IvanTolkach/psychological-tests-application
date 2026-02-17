package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.endpoint;

import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto.MethodologyDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface MethodologyEndpoint {
    @PostMapping(ApiEndpoints.Methodology.SEARCH)
    ResponseEntity<List<MethodologyDto>> getMethodologies(@RequestBody MethodologyDto filter);

    @PostMapping(ApiEndpoints.Methodology.BASE)
    ResponseEntity<MethodologyDto> createUpdateMethodology(@Valid @RequestBody MethodologyDto dto);

    @DeleteMapping(ApiEndpoints.Methodology.BY_ID)
    ResponseEntity<Void> deleteMethodology(@PathVariable UUID methodologyId);
}
