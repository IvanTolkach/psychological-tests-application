package dev.tolkach.methodologiesservice.adapter.in.rest.endpoint;

import dev.tolkach.methodologiesservice.adapter.in.rest.dto.ScoreRangeDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface ScoreRangeEndpoint {
    @PostMapping(ApiEndpoints.ScoreRange.SEARCH)
    @PreAuthorize("permitAll()")
    ResponseEntity<List<ScoreRangeDto>> getScoreRanges(@RequestBody ScoreRangeDto filter);

    @PostMapping(ApiEndpoints.ScoreRange.BASE)
    @PreAuthorize("hasAuthority('ROLE_SUPER')")
    ResponseEntity<ScoreRangeDto> createUpdateScoreRange(@Valid @RequestBody ScoreRangeDto dto);

    @DeleteMapping(ApiEndpoints.ScoreRange.BY_ID)
    @PreAuthorize("hasAuthority('ROLE_SUPER')")
    ResponseEntity<Void> deleteScoreRange(@PathVariable UUID scoreRangeId);
}
