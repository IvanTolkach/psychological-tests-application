package dev.tolkach.psychologicalTestsApplication.domain.port.in;

import dev.tolkach.psychologicalTestsApplication.domain.model.Question;

import java.util.List;
import java.util.UUID;

public interface QuestionUseCase {
    List<Question> getQuestionsByFilter(Question filter);
    Question createUpdateQuestion(Question question);
    void deleteQuestion(UUID id);
}
