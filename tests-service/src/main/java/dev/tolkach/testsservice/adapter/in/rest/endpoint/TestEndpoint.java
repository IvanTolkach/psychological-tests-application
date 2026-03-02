package dev.tolkach.testsservice.adapter.in.rest.endpoint;

import dev.tolkach.testsservice.adapter.in.rest.dto.TestDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public interface TestEndpoint {
    @PostMapping(ApiEndpoints.Test.SEARCH)
    ResponseEntity<List<TestDto>> getTests(@RequestBody TestDto filter);

    @GetMapping(ApiEndpoints.Test.BY_ID)
    ResponseEntity<TestDto> getTestById(@PathVariable UUID testId);

    @PostMapping(ApiEndpoints.Test.BASE)
    ResponseEntity<TestDto> createUpdateTest(@Valid @RequestBody TestDto dto);

    @PatchMapping(ApiEndpoints.Test.UPDATE_STATUS)
    ResponseEntity<Void> updateTestStatus(@PathVariable UUID testId);

    @DeleteMapping(ApiEndpoints.Test.BY_ID)
    ResponseEntity<Void> deleteTest(@PathVariable UUID testId);
}
