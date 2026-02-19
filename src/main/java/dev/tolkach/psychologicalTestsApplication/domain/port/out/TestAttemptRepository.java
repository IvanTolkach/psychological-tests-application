package dev.tolkach.psychologicalTestsApplication.domain.port.out;

import dev.tolkach.psychologicalTestsApplication.domain.model.TestAttempt;
import dev.tolkach.psychologicalTestsApplication.domain.model.TestAttemptFilter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TestAttemptRepository {
    TestAttempt save(TestAttempt testAttempt);
    Optional<TestAttempt> findById(UUID id);
    List<TestAttempt> findByFilter(TestAttemptFilter filter);
    void deleteById(UUID id);
}
