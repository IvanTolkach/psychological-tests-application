package dev.tolkach.attemptsservice.adapter.in.rest;

import dev.tolkach.attemptsservice.adapter.in.rest.dto.TestAttemptScoreDto;
import dev.tolkach.attemptsservice.adapter.in.rest.endpoint.TestAttemptScoreEndpoint;
import dev.tolkach.attemptsservice.adapter.in.rest.mapper.TestAttemptScoreDtoMapper;
import dev.tolkach.attemptsservice.application.model.TestAttemptScore;
import dev.tolkach.attemptsservice.application.port.in.TestAttemptScoreUseCase;
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
    public ResponseEntity<List<TestAttemptScoreDto>> createUpdateTestAttemptScore(TestAttemptScoreDto dto) {

        TestAttemptScore entity = testAttemptScoreDtoMapper.toEntity(dto);

        List<TestAttemptScore> saved =
                testAttemptScoreUseCase.createUpdateTestAttemptScore(entity);

        List<TestAttemptScoreDto> response =
                saved.stream()
                        .map(testAttemptScoreDtoMapper::toDto)
                        .toList();

        if (dto.getId() == null) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);
        } else {
            return ResponseEntity.ok(response);
        }
    }

    @Override
    public ResponseEntity<Void> deleteTestAttemptScore(UUID testAttemptScoreId) {
        testAttemptScoreUseCase.deleteTestAttemptScore(testAttemptScoreId);
        return ResponseEntity.noContent().build();
    }
}
