package dev.tolkach.psychologicalTestsApplication.domain.port.out;

import dev.tolkach.psychologicalTestsApplication.domain.model.ScaleQuestion;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ScaleQuestionRepository {
    ScaleQuestion save(ScaleQuestion scaleQuestion);
    Optional<ScaleQuestion> findById(UUID id);
    List<ScaleQuestion> findByFilter(ScaleQuestion filter);
    void deleteById(UUID id);
}
