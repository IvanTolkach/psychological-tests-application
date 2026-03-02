package dev.tolkach.testsservice.application.service;

import dev.tolkach.testsservice.application.model.Question;
import dev.tolkach.testsservice.application.port.in.QuestionUseCase;
import dev.tolkach.testsservice.application.port.out.QuestionRepository;
import dev.tolkach.testsservice.application.port.out.TestRepository;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

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
                    .orElseThrow(() -> new NoSuchElementException("Test not found with id: " + filter.getTestId()));
        }
        return questionRepository.findByFilter(filter);
    }

    @Override
    public Question getQuestionById(UUID id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Question not found with id: " + id));
    }

    //TODO подумать над оптимизацией
    @Override
    public Question createUpdateQuestion(Question question) {
        testRepository.findById(question.getTestId())
                .orElseThrow(() -> new NoSuchElementException("Test not found with id: " + question.getTestId()));

        Question filter = new Question();
        filter.setTestId(question.getTestId());
        List<Question> allQuestions = questionRepository.findByFilter(filter).stream()
                .sorted(Comparator.comparingInt(q -> q.getPosition() != null ? q.getPosition() : Integer.MAX_VALUE))
                .collect(Collectors.toList());

        if (question.getText() != null && !question.getText().isBlank()) {
            Question textCheck = new Question();
            textCheck.setTestId(question.getTestId());
            textCheck.setText(question.getText());

            List<Question> existingWithSameText = questionRepository.findByFilter(textCheck);

            boolean textConflict = existingWithSameText.stream()
                    .anyMatch(q -> !q.getId().equals(question.getId()));

            if (textConflict) {
                throw new IllegalArgumentException(
                        "Question with text '" + question.getText() + "' already exists in test " + question.getTestId()
                );
            }
        }

        Integer requestedPosition = question.getPosition();

        int maxPosition = allQuestions.stream()
                .mapToInt(q -> q.getPosition() != null ? q.getPosition() : 0)
                .max()
                .orElse(0);

        if (question.getId() == null) {
            if (requestedPosition == null || requestedPosition <= 0 || requestedPosition > maxPosition + 1) {
                question.setPosition(maxPosition + 1);
            } else {
                shiftPositionsDown(allQuestions, requestedPosition);
                question.setPosition(requestedPosition);
            }

            return questionRepository.save(question);
        } else {
            Question existing = questionRepository.findById(question.getId())
                    .orElseThrow(() -> new NoSuchElementException("Question not found with id: " + question.getId()));

            if (!existing.getTestId().equals(question.getTestId())) {
                throw new IllegalArgumentException("Cannot move question to another test");
            }

            if (requestedPosition == null || requestedPosition.equals(existing.getPosition())) {
                existing.setText(question.getText());
                existing.setType(question.getType());
                return questionRepository.save(existing);
            }

            int oldPosition = existing.getPosition() != null ? existing.getPosition() : maxPosition + 1;
            int newPosition = requestedPosition;

            if (newPosition <= 0 || newPosition > maxPosition + 1) {
                newPosition = maxPosition;
            }

            allQuestions.remove(existing);

            shiftPositionsForMove(allQuestions, oldPosition, newPosition);

            existing.setText(question.getText());
            existing.setType(question.getType());
            existing.setPosition(newPosition);

            return questionRepository.save(existing);
        }
    }

    @Override
    public void deleteQuestion(UUID id) {
        Question questionToDelete = questionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Question not found with id: " + id));

        Question filter = new Question();
        filter.setTestId(questionToDelete.getTestId());
        List<Question> allQuestions = questionRepository.findByFilter(filter).stream()
                .sorted(Comparator.comparingInt(q -> q.getPosition() != null ? q.getPosition() : Integer.MAX_VALUE))
                .collect(Collectors.toList());

        questionRepository.deleteById(id);

        int deletedPos = questionToDelete.getPosition() != null ? questionToDelete.getPosition() : 0;
        shiftPositionsUpAfterDelete(allQuestions, deletedPos);
    }

    private void shiftPositionsDown(List<Question> questions, int targetPosition) {
        for (Question q : questions) {
            Integer pos = q.getPosition();
            if (pos != null && pos >= targetPosition) {
                q.setPosition(pos + 1);
                questionRepository.save(q);
            }
        }
    }

    private void shiftPositionsForMove(List<Question> questions, int oldPosition, int newPosition) {
        if (newPosition == oldPosition) return;

        if (newPosition < oldPosition) {
            for (Question q : questions) {
                Integer pos = q.getPosition();
                if (pos != null && pos >= newPosition && pos < oldPosition) {
                    q.setPosition(pos + 1);
                    questionRepository.save(q);
                }
            }
        } else {
            for (Question q : questions) {
                Integer pos = q.getPosition();
                if (pos != null && pos > oldPosition && pos <= newPosition) {
                    q.setPosition(pos - 1);
                    questionRepository.save(q);
                }
            }
        }
    }

    private void shiftPositionsUpAfterDelete(List<Question> questions, int deletedPos) {
        for (Question q : questions) {
            Integer pos = q.getPosition();
            if (pos != null && pos > deletedPos) {
                q.setPosition(pos - 1);
                questionRepository.save(q);
            }
        }
    }
}
