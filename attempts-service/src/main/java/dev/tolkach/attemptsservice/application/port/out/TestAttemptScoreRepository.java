package dev.tolkach.attemptsservice.application.port.out;

import dev.tolkach.attemptsservice.application.model.TestAttemptScore;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TestAttemptScoreRepository {
    TestAttemptScore save(TestAttemptScore testAttemptScore);
    Optional<TestAttemptScore> findById(UUID id);
    List<TestAttemptScore> findByFilter(TestAttemptScore filter);
    void deleteById(UUID id);
}
