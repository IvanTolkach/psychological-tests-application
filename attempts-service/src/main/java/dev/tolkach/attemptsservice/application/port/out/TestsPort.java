package dev.tolkach.attemptsservice.application.port.out;

import java.util.UUID;

public interface TestsPort {
    void validateQuestionExists(UUID questionId);
    void validateTestExists(UUID testId);
    void validateAnswerOptionExists(UUID answerOptionId);
}
