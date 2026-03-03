package dev.tolkach.attemptsservice.application.port.out;

import common.dto.AnswerOptionDto;
import common.dto.QuestionDto;
import common.dto.TestDto;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface TestsPort {
    void validateQuestionExists(UUID questionId);
    void validateTestExists(UUID testId);
    void validateAnswerOptionExists(UUID answerOptionId);
    TestDto getTestById(UUID testId);
    List<QuestionDto> getQuestionsByTestId(UUID testId);
    List<AnswerOptionDto> getAnswerOptionsByQuestionIds(Collection<UUID> questionIds);
}
