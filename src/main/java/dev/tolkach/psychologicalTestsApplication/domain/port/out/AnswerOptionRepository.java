package dev.tolkach.psychologicalTestsApplication.domain.port.out;

import dev.tolkach.psychologicalTestsApplication.domain.model.AnswerOption;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnswerOptionRepository {
    AnswerOption save(AnswerOption answerOption);
    Optional<AnswerOption> findById(UUID id);
    List<AnswerOption> findByFilter(AnswerOption filter);
    void deleteById(UUID id);
}
