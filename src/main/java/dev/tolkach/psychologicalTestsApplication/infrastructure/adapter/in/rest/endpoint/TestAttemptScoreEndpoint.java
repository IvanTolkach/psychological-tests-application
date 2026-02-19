package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.endpoint;

import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto.TestAttemptScoreDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface TestAttemptScoreEndpoint {
    @PostMapping(ApiEndpoints.TestAttemptScore.SEARCH)
    ResponseEntity<List<TestAttemptScoreDto>> getTestAttemptScores(@RequestBody TestAttemptScoreDto filter);

    @PostMapping(ApiEndpoints.TestAttemptScore.BASE)
    ResponseEntity<TestAttemptScoreDto> createUpdateTestAttemptScore(@Valid @RequestBody TestAttemptScoreDto dto);

    @DeleteMapping(ApiEndpoints.TestAttemptScore.BY_ID)
    ResponseEntity<Void> deleteTestAttemptScore(@PathVariable UUID testAttemptScoreId);
}
