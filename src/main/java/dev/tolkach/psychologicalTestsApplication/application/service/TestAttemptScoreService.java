package dev.tolkach.psychologicalTestsApplication.application.service;

import dev.tolkach.psychologicalTestsApplication.domain.model.TestAttemptScore;
import dev.tolkach.psychologicalTestsApplication.domain.port.in.TestAttemptScoreUseCase;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.ScaleRepository;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.TestAttemptRepository;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.TestAttemptScoreRepository;

import java.util.List;
import java.util.UUID;

public class TestAttemptScoreService implements TestAttemptScoreUseCase {

    private final TestAttemptScoreRepository testAttemptScoreRepository;
    private final TestAttemptRepository testAttemptRepository;
    private final ScaleRepository scaleRepository;

    public TestAttemptScoreService(TestAttemptScoreRepository testAttemptScoreRepository, TestAttemptRepository testAttemptRepository, ScaleRepository scaleRepository) {
        this.testAttemptScoreRepository = testAttemptScoreRepository;
        this.testAttemptRepository = testAttemptRepository;
        this.scaleRepository = scaleRepository;
    }

    @Override
    public List<TestAttemptScore> getTestAttemptScoresByFilter(TestAttemptScore filter) {
        if (filter.getTestAttemptId() != null) {
            testAttemptRepository.findById(filter.getTestAttemptId())
                    .orElseThrow(() -> new IllegalArgumentException("TestAttempt not found with id: " + filter.getTestAttemptId()));
        }
        if (filter.getScaleId() != null) {
            scaleRepository.findById(filter.getScaleId())
                    .orElseThrow(() -> new IllegalArgumentException("Scale not found with id: " + filter.getScaleId()));
        }
        return testAttemptScoreRepository.findByFilter(filter);
    }

    @Override
    public TestAttemptScore createUpdateTestAttemptScore(TestAttemptScore testAttemptScore) {
        testAttemptRepository.findById(testAttemptScore.getTestAttemptId())
                .orElseThrow(() -> new IllegalArgumentException("TestAttempt not found with id: " + testAttemptScore.getTestAttemptId()));

        scaleRepository.findById(testAttemptScore.getScaleId())
                .orElseThrow(() -> new IllegalArgumentException("Scale not found with id: " + testAttemptScore.getScaleId()));

        TestAttemptScore checkFilter = new TestAttemptScore();
        checkFilter.setTestAttemptId(testAttemptScore.getTestAttemptId());
        checkFilter.setScaleId(testAttemptScore.getScaleId());

        List<TestAttemptScore> existingScores = testAttemptScoreRepository.findByFilter(checkFilter);

        boolean alreadyScored = existingScores.stream()
                .anyMatch(s -> !s.getId().equals(testAttemptScore.getId()));

        if (alreadyScored) {
            throw new IllegalArgumentException(
                    "Score for scale " + testAttemptScore.getScaleId() +
                            " already exists in attempt " + testAttemptScore.getTestAttemptId()
            );
        }

        if (testAttemptScore.getId() == null) {
            return testAttemptScoreRepository.save(testAttemptScore);
        } else {
            TestAttemptScore existing = testAttemptScoreRepository.findById(testAttemptScore.getId())
                    .orElseThrow(() -> new IllegalArgumentException("TestAttemptScore not found with id: " + testAttemptScore.getId()));

            existing.setTestAttemptId(testAttemptScore.getTestAttemptId());
            existing.setScaleId(testAttemptScore.getScaleId());
            existing.setScore(testAttemptScore.getScore());

            return testAttemptScoreRepository.save(existing);
        }
    }

    @Override
    public void deleteTestAttemptScore(UUID id) {
        if (testAttemptScoreRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("TestAttemptScore not found with id: " + id);
        }
        testAttemptScoreRepository.deleteById(id);
    }
}
