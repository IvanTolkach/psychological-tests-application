package dev.tolkach.attemptsservice.application.port.out;

import dev.tolkach.attemptsservice.application.model.TestAttempt;
import dev.tolkach.attemptsservice.application.model.TestAttemptFilter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TestAttemptRepository {
    TestAttempt save(TestAttempt testAttempt);
    Optional<TestAttempt> findById(UUID id);
    List<TestAttempt> findByFilter(TestAttemptFilter filter);
    void deleteById(UUID id);
}
