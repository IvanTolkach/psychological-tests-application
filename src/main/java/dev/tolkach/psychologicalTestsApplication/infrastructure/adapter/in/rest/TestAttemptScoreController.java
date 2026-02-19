package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest;

import dev.tolkach.psychologicalTestsApplication.domain.model.TestAttemptScore;
import dev.tolkach.psychologicalTestsApplication.domain.port.in.TestAttemptScoreUseCase;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto.TestAttemptScoreDto;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.endpoint.TestAttemptScoreEndpoint;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.mapper.TestAttemptScoreDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class TestAttemptScoreController implements TestAttemptScoreEndpoint {

    private final TestAttemptScoreUseCase testAttemptScoreUseCase;
    private final TestAttemptScoreDtoMapper testAttemptScoreDtoMapper;

    public TestAttemptScoreController(TestAttemptScoreUseCase testAttemptScoreUseCase, TestAttemptScoreDtoMapper testAttemptScoreDtoMapper) {
        this.testAttemptScoreUseCase = testAttemptScoreUseCase;
        this.testAttemptScoreDtoMapper = testAttemptScoreDtoMapper;
    }

    @Override
    public ResponseEntity<List<TestAttemptScoreDto>> getTestAttemptScores(TestAttemptScoreDto filter) {
        TestAttemptScore testAttemptScoreFilter = testAttemptScoreDtoMapper.toEntity(filter);
        List<TestAttemptScore> testAttemptScores = testAttemptScoreUseCase.getTestAttemptScoresByFilter(testAttemptScoreFilter);
        List<TestAttemptScoreDto> responseDtos = testAttemptScores.stream().map(testAttemptScoreDtoMapper::toDto).toList();
        return ResponseEntity.ok(responseDtos);
    }

    @Override
    public ResponseEntity<TestAttemptScoreDto> createUpdateTestAttemptScore(TestAttemptScoreDto dto) {
        TestAttemptScore testAttemptScore = testAttemptScoreDtoMapper.toEntity(dto);
        TestAttemptScore saved = testAttemptScoreUseCase.createUpdateTestAttemptScore(testAttemptScore);
        TestAttemptScoreDto responseDto = testAttemptScoreDtoMapper.toDto(saved);
        if (dto.getId() == null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } else {
            return ResponseEntity.ok(responseDto);
        }
    }

    @Override
    public ResponseEntity<Void> deleteTestAttemptScore(UUID testAttemptScoreId) {
        testAttemptScoreUseCase.deleteTestAttemptScore(testAttemptScoreId);
        return ResponseEntity.noContent().build();
    }
}
