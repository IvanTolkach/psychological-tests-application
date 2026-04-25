package dev.tolkach.attemptsservice.adapter.in.rest.endpoint;

import dev.tolkach.attemptsservice.adapter.in.rest.dto.TestAttemptScoreDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface TestAttemptScoreEndpoint {
    @PostMapping(ApiEndpoints.TestAttemptScore.SEARCH)
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<List<TestAttemptScoreDto>> getTestAttemptScores(@RequestBody TestAttemptScoreDto filter);

    @PostMapping(ApiEndpoints.TestAttemptScore.BASE)
    @PreAuthorize("#p0.id == null or (isAuthenticated() and hasAuthority('ROLE_SUPER'))")
    ResponseEntity<List<TestAttemptScoreDto>> createUpdateTestAttemptScore(@Valid @RequestBody TestAttemptScoreDto dto);

    @DeleteMapping(ApiEndpoints.TestAttemptScore.BY_ID)
    @PreAuthorize("hasAuthority('ROLE_SUPER')")
    ResponseEntity<Void> deleteTestAttemptScore(@PathVariable UUID testAttemptScoreId);
}
