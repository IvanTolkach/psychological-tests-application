package dev.tolkach.attemptsservice.application.service;

import dev.tolkach.attemptsservice.application.model.TestAttemptScore;
import dev.tolkach.attemptsservice.application.port.in.TestAttemptScoreUseCase;
import dev.tolkach.attemptsservice.application.port.out.MethodologiesPort;
import dev.tolkach.attemptsservice.application.port.out.TestAttemptRepository;
import dev.tolkach.attemptsservice.application.port.out.TestAttemptScoreRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class TestAttemptScoreService implements TestAttemptScoreUseCase {

    private final TestAttemptScoreRepository testAttemptScoreRepository;
    private final TestAttemptRepository testAttemptRepository;
    private final MethodologiesPort methodologiesPort;

    public TestAttemptScoreService(TestAttemptScoreRepository testAttemptScoreRepository, TestAttemptRepository testAttemptRepository, MethodologiesPort methodologiesPort) {
        this.testAttemptScoreRepository = testAttemptScoreRepository;
        this.testAttemptRepository = testAttemptRepository;
        this.methodologiesPort = methodologiesPort;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestAttemptScore> getTestAttemptScoresByFilter(TestAttemptScore filter) {
        if (filter.getTestAttemptId() != null) {
            testAttemptRepository.findById(filter.getTestAttemptId())
                    .orElseThrow(() -> new NoSuchElementException("TestAttempt not found with id: " + filter.getTestAttemptId()));
        }
        if (filter.getScaleId() != null) {
            methodologiesPort.validateScaleExists(filter.getScaleId());
        }
        return testAttemptScoreRepository.findByFilter(filter);
    }

    @Override
    @Transactional
    public TestAttemptScore createUpdateTestAttemptScore(TestAttemptScore testAttemptScore) {
        testAttemptRepository.findById(testAttemptScore.getTestAttemptId())
                .orElseThrow(() -> new NoSuchElementException("TestAttempt not found with id: " + testAttemptScore.getTestAttemptId()));

        methodologiesPort.validateScaleExists(testAttemptScore.getScaleId());

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
                    .orElseThrow(() -> new NoSuchElementException("TestAttemptScore not found with id: " + testAttemptScore.getId()));

            existing.setTestAttemptId(testAttemptScore.getTestAttemptId());
            existing.setScaleId(testAttemptScore.getScaleId());
            existing.setScore(testAttemptScore.getScore());

            return testAttemptScoreRepository.save(existing);
        }
    }

    @Override
    @Transactional
    public void deleteTestAttemptScore(UUID id) {
        if (testAttemptScoreRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException("TestAttemptScore not found with id: " + id);
        }
        testAttemptScoreRepository.deleteById(id);
    }
}
