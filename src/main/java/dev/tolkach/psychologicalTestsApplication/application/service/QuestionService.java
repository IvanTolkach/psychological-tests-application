package dev.tolkach.psychologicalTestsApplication.application.service;

import dev.tolkach.psychologicalTestsApplication.domain.model.Question;
import dev.tolkach.psychologicalTestsApplication.domain.port.in.QuestionUseCase;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.QuestionRepository;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.TestRepository;

import java.util.List;
import java.util.UUID;

public class QuestionService implements QuestionUseCase {

    private final QuestionRepository questionRepository;
    private final TestRepository testRepository;

    public QuestionService(QuestionRepository questionRepository, TestRepository testRepository) {
        this.questionRepository = questionRepository;
        this.testRepository = testRepository;
    }

    @Override
    public List<Question> getQuestionsByFilter(Question filter) {
        if (filter.getTestId() != null) {
            testRepository.findById(filter.getTestId())
                    .orElseThrow(() -> new IllegalArgumentException("Test not found with id: " + filter.getTestId()));
        }
        return questionRepository.findByFilter(filter);
    }

    @Override
    public Question createUpdateQuestion(Question question) {
        testRepository.findById(question.getTestId())
                .orElseThrow(() -> new IllegalArgumentException("Test not found with id: " + question.getTestId()));

        if (question.getId() == null) {
            return questionRepository.save(question);
        } else {
            Question existing = questionRepository.findById(question.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Question not found with id: " + question.getId()));

            existing.setText(question.getText());
            existing.setTestId(question.getTestId());
            existing.setType(question.getType());
            existing.setPosition(question.getPosition());

            return questionRepository.save(existing);
        }
    }

    @Override
    public void deleteQuestion(UUID id) {
        if (questionRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Question not found with id: " + id);
        }
        questionRepository.deleteById(id);
    }
}
