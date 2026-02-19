package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest;

import dev.tolkach.psychologicalTestsApplication.domain.model.TestAttempt;
import dev.tolkach.psychologicalTestsApplication.domain.model.TestAttemptFilter;
import dev.tolkach.psychologicalTestsApplication.domain.port.in.TestAttemptUseCase;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto.TestAttemptDto;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.endpoint.TestAttemptEndpoint;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.mapper.TestAttemptDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class TestAttemptController implements TestAttemptEndpoint {

    private final TestAttemptUseCase testAttemptUseCase;
    private final TestAttemptDtoMapper testAttemptDtoMapper;

    public TestAttemptController(TestAttemptUseCase testAttemptUseCase, TestAttemptDtoMapper testAttemptDtoMapper) {
        this.testAttemptUseCase = testAttemptUseCase;
        this.testAttemptDtoMapper = testAttemptDtoMapper;
    }

    @Override
    public ResponseEntity<List<TestAttemptDto>> getTestAttempts(TestAttemptDto filter) {
        TestAttemptFilter testAttemptFilter = testAttemptDtoMapper.toFilter(filter);
        List<TestAttempt> testAttempts = testAttemptUseCase.getTestAttemptsByFilter(testAttemptFilter);
        List<TestAttemptDto> responseDtos = testAttempts.stream().map(testAttemptDtoMapper::toDto).toList();
        return ResponseEntity.ok(responseDtos);
    }

    @Override
    public ResponseEntity<TestAttemptDto> createTestAttempt(TestAttemptDto dto) {
        TestAttempt testAttempt = testAttemptDtoMapper.toEntity(dto);
        TestAttempt saved = testAttemptUseCase.createUpdateTestAttempt(testAttempt);
        TestAttemptDto responseDto = testAttemptDtoMapper.toDto(saved);
        if (dto.getId() == null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } else {
            return ResponseEntity.ok(responseDto);
        }
    }

    @Override
    public ResponseEntity<Void> deleteTestAttempt(UUID testAttemptId) {
        testAttemptUseCase.deleteTestAttempt(testAttemptId);
        return ResponseEntity.noContent().build();
    }
}