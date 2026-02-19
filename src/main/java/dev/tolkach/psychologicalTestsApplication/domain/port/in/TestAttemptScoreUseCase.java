package dev.tolkach.psychologicalTestsApplication.domain.port.in;

import dev.tolkach.psychologicalTestsApplication.domain.model.TestAttemptScore;

import java.util.List;
import java.util.UUID;

public interface TestAttemptScoreUseCase {
    List<TestAttemptScore> getTestAttemptScoresByFilter(TestAttemptScore filter);
    TestAttemptScore createUpdateTestAttemptScore(TestAttemptScore testAttemptScore);
    void deleteTestAttemptScore(UUID id);
}
