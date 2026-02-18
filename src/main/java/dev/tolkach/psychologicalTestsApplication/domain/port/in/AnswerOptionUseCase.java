package dev.tolkach.psychologicalTestsApplication.domain.port.in;

import dev.tolkach.psychologicalTestsApplication.domain.model.AnswerOption;

import java.util.List;
import java.util.UUID;

public interface AnswerOptionUseCase {
    List<AnswerOption> getAnswerOptionsByFilter(AnswerOption filter);
    AnswerOption createUpdateAnswerOption(AnswerOption answerOption);
    void deleteAnswerOption(UUID id);
}
