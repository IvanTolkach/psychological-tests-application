package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.endpoint;

import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto.ScaleDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface ScaleEndpoint {
    @PostMapping(ApiEndpoints.Scale.SEARCH)
    ResponseEntity<List<ScaleDto>> getScales(@RequestBody ScaleDto filter);

    @PostMapping(ApiEndpoints.Scale.BASE)
    ResponseEntity<ScaleDto> createUpdateScale(@Valid @RequestBody ScaleDto dto);

    @DeleteMapping(ApiEndpoints.Scale.BY_ID)
    ResponseEntity<Void> deleteScale(@PathVariable UUID scaleId);
}
