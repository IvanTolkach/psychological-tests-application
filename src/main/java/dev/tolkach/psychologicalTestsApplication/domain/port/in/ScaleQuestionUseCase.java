package dev.tolkach.psychologicalTestsApplication.domain.port.in;

import dev.tolkach.psychologicalTestsApplication.domain.model.ScaleQuestion;

import java.util.List;
import java.util.UUID;

public interface ScaleQuestionUseCase {
    List<ScaleQuestion> getScaleQuestionsByFilter(ScaleQuestion filter);
    ScaleQuestion createScaleQuestion(ScaleQuestion scaleQuestion);
    void deleteScaleQuestion(UUID id);
}
