package dev.tolkach.testsservice.application.port.in;

import dev.tolkach.testsservice.application.model.AnswerOption;

import java.util.List;
import java.util.UUID;

public interface AnswerOptionUseCase {
    List<AnswerOption> getAnswerOptionsByFilter(AnswerOption filter);
    AnswerOption getAnswerOptionById(UUID id);
    AnswerOption createUpdateAnswerOption(AnswerOption answerOption);
    void deleteAnswerOption(UUID id);
}
