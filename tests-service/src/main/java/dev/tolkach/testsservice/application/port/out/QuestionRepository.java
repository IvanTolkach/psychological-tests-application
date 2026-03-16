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
    void shiftDown(UUID testId, int position);
    void shiftUp(UUID testId, int position);
    void shiftForMoveUp(UUID testId, int newPos, int oldPos);
    void shiftForMoveDown(UUID testId, int oldPos, int newPos);
    int getMaxPosition(UUID testId);
}
