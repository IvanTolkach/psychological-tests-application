package dev.tolkach.methodologiesservice.application.port.out;

import java.util.UUID;

public interface TestsPort {
    void validateQuestionExists(UUID questionId);
}
