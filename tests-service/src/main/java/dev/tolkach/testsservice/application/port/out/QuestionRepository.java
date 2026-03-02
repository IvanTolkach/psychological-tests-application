package dev.tolkach.testsservice.application.port.out;

import dev.tolkach.testsservice.application.model.Question;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuestionRepository {
    Question save(Question question);
    Optional<Question> findById(UUID id);
    List<Question> findByFilter(Question filter);
    void deleteById(UUID id);
}
