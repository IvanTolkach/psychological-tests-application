package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.endpoint;

import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto.TestAttemptDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface TestAttemptEndpoint {
    @PostMapping(ApiEndpoints.TestAttempt.SEARCH)
    ResponseEntity<List<TestAttemptDto>> getTestAttempts(@RequestBody TestAttemptDto filter);

    @PostMapping(ApiEndpoints.TestAttempt.BASE)
    ResponseEntity<TestAttemptDto> createTestAttempt(@Valid @RequestBody TestAttemptDto dto);

    @DeleteMapping(ApiEndpoints.TestAttempt.BY_ID)
    ResponseEntity<Void> deleteTestAttempt(@PathVariable UUID testAttemptId);
}
