package dev.tolkach.testsservice.application.port.in;

import dev.tolkach.testsservice.application.model.Question;

import java.util.List;
import java.util.UUID;

public interface QuestionUseCase {
    List<Question> getQuestionsByFilter(Question filter);
    Question getQuestionById(UUID id);
    Question createUpdateQuestion(Question question);
    void deleteQuestion(UUID id);
}
