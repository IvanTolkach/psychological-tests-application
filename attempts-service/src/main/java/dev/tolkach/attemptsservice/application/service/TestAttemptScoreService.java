package dev.tolkach.attemptsservice.application.service;

import common.dto.*;
import dev.tolkach.attemptsservice.application.model.ScaleScoreResult;
import dev.tolkach.attemptsservice.application.model.TestAttemptScore;
import dev.tolkach.attemptsservice.application.port.in.TestAttemptScoreUseCase;
import dev.tolkach.attemptsservice.application.port.out.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

public class TestAttemptScoreService implements TestAttemptScoreUseCase {

    private final TestAttemptScoreRepository testAttemptScoreRepository;
    private final TestAttemptRepository testAttemptRepository;
    private final MethodologiesPort methodologiesPort;
    private final StudentAnswerRepository studentAnswerRepository;

    public TestAttemptScoreService(TestAttemptScoreRepository testAttemptScoreRepository, TestAttemptRepository testAttemptRepository, MethodologiesPort methodologiesPort, StudentAnswerRepository studentAnswerRepository) {
        this.testAttemptScoreRepository = testAttemptScoreRepository;
        this.testAttemptRepository = testAttemptRepository;
        this.methodologiesPort = methodologiesPort;
        this.studentAnswerRepository = studentAnswerRepository;
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
    public List<TestAttemptScore> createUpdateTestAttemptScore(TestAttemptScore testAttemptScore) {

        UUID attemptId = testAttemptScore.getTestAttemptId();

        if (attemptId == null) {
            throw new IllegalArgumentException("testAttemptId must be provided");
        }

        testAttemptRepository.findById(attemptId)
                .orElseThrow(() -> new NoSuchElementException("TestAttempt not found with id: " + attemptId));

        if (testAttemptScore.getId() == null) {

            List<ScaleScoreResult> results = studentAnswerRepository.calculateScoresWithInterpretation(attemptId);

            if (results.isEmpty()) {
                throw new IllegalArgumentException("Scores cannot be calculated for attempt " + attemptId);
            }

            TestAttemptScore filter = new TestAttemptScore();
            filter.setTestAttemptId(attemptId);

            List<TestAttemptScore> existing =
                    testAttemptScoreRepository.findByFilter(filter);

            Set<UUID> existingScaleIds = existing.stream()
                    .map(TestAttemptScore::getScaleId)
                    .collect(Collectors.toSet());

            List<TestAttemptScore> toSave = new ArrayList<>();

            for (ScaleScoreResult r : results) {

                if (existingScaleIds.contains(r.getScaleId())) {
                    continue;
                }

                if (r.getInterpretation() == null) {
                    throw new IllegalArgumentException("Interpretation is null for scaleId: " + r.getScaleId());
                }

                TestAttemptScore score = new TestAttemptScore();
                score.setTestAttemptId(attemptId);
                score.setScaleId(r.getScaleId());
                score.setScore(r.getScore());
                score.setInterpretation(r.getInterpretation());

                toSave.add(score);
            }

            if (!toSave.isEmpty()) {
                return testAttemptScoreRepository.saveAll(toSave);
            }

            return existing;
        }

        else {

            TestAttemptScore existing = testAttemptScoreRepository.findById(testAttemptScore.getId())
                    .orElseThrow(() -> new NoSuchElementException("TestAttemptScore not found with id: " + testAttemptScore.getId()));

            if (testAttemptScore.getScore() != null) {
                existing.setScore(testAttemptScore.getScore());
            }
            if (testAttemptScore.getInterpretation() != null) {
                existing.setInterpretation(testAttemptScore.getInterpretation());
            }

            TestAttemptScore updated = testAttemptScoreRepository.save(existing);

            return List.of(updated);
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
