package dev.tolkach.attemptsservice.adapter.in.rest.endpoint;

import dev.tolkach.attemptsservice.adapter.in.rest.dto.TestAttemptDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface TestAttemptEndpoint {
    @PostMapping(ApiEndpoints.TestAttempt.SEARCH)
    @PreAuthorize("permitAll()")
    ResponseEntity<List<TestAttemptDto>> getTestAttempts(@RequestBody TestAttemptDto filter);

    @PostMapping(ApiEndpoints.TestAttempt.BASE)
    @PreAuthorize("#p0.id == null or (isAuthenticated() and hasAuthority('ROLE_SUPER'))")
    ResponseEntity<TestAttemptDto> createTestAttempt(@Valid @RequestBody TestAttemptDto dto);

    @DeleteMapping(ApiEndpoints.TestAttempt.BY_ID)
    @PreAuthorize("hasAuthority('ROLE_SUPER')")
    ResponseEntity<Void> deleteTestAttempt(@PathVariable UUID testAttemptId);
}
