package dev.tolkach.attemptsservice.application.port.in;

import dev.tolkach.attemptsservice.application.model.TestAttemptScore;

import java.util.List;
import java.util.UUID;

public interface TestAttemptScoreUseCase {
    List<TestAttemptScore> getTestAttemptScoresByFilter(TestAttemptScore filter);
    TestAttemptScore createUpdateTestAttemptScore(TestAttemptScore testAttemptScore);
    void deleteTestAttemptScore(UUID id);
}
