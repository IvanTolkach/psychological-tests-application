package dev.tolkach.methodologiesservice.adapter.in.rest.endpoint;

import dev.tolkach.methodologiesservice.adapter.in.rest.dto.ScoreRangeDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface ScoreRangeEndpoint {
    @PostMapping(ApiEndpoints.ScoreRange.SEARCH)
    ResponseEntity<List<ScoreRangeDto>> getScoreRanges(@RequestBody ScoreRangeDto filter);

    @PostMapping(ApiEndpoints.ScoreRange.BASE)
    ResponseEntity<ScoreRangeDto> createUpdateScoreRange(@Valid @RequestBody ScoreRangeDto dto);

    @DeleteMapping(ApiEndpoints.ScoreRange.BY_ID)
    ResponseEntity<Void> deleteScoreRange(@PathVariable UUID scoreRangeId);
}
