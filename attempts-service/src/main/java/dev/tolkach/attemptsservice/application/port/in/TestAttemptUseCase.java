package dev.tolkach.attemptsservice.application.port.in;

import dev.tolkach.attemptsservice.application.model.TestAttempt;
import dev.tolkach.attemptsservice.application.model.TestAttemptFilter;

import java.util.List;
import java.util.UUID;

public interface TestAttemptUseCase {
    List<TestAttempt> getTestAttemptsByFilter(TestAttemptFilter filter);
    TestAttempt createUpdateTestAttempt(TestAttempt testAttempt);
    void deleteTestAttempt(UUID id);
}
