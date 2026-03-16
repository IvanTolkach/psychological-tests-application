package dev.tolkach.testsservice.application.service;

import dev.tolkach.testsservice.application.model.Question;
import dev.tolkach.testsservice.application.port.in.QuestionUseCase;
import dev.tolkach.testsservice.application.port.out.QuestionRepository;
import dev.tolkach.testsservice.application.port.out.TestRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class QuestionService implements QuestionUseCase {

    private final QuestionRepository questionRepository;
    private final TestRepository testRepository;

    public QuestionService(QuestionRepository questionRepository, TestRepository testRepository) {
        this.questionRepository = questionRepository;
        this.testRepository = testRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Question> getQuestionsByFilter(Question filter) {
        if (filter.getTestId() != null) {
            testRepository.findById(filter.getTestId())
                    .orElseThrow(() -> new NoSuchElementException("Test not found with id: " + filter.getTestId()));
        }
        return questionRepository.findByFilter(filter);
    }

    @Override
    @Transactional(readOnly = true)
    public Question getQuestionById(UUID id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Question not found with id: " + id));
    }

    @Override
    @Transactional
    public Question createUpdateQuestion(Question question) {
        testRepository.findById(question.getTestId())
                .orElseThrow(() -> new NoSuchElementException("Test not found with id: " + question.getTestId()));

        if (question.getId() == null) {

            checkTextUnique(question);

            int maxPosition = questionRepository.getMaxPosition(question.getTestId());

            Integer requestedPosition = question.getPosition();

            if (requestedPosition == null ||
                    requestedPosition <= 0 ||
                    requestedPosition > maxPosition + 1) {

                question.setPosition(maxPosition + 1);

            } else {

                questionRepository.shiftDown(
                        question.getTestId(),
                        requestedPosition
                );

                question.setPosition(requestedPosition);
            }

            return questionRepository.save(question);
        }
        else {

            Question existing = questionRepository.findById(question.getId())
                    .orElseThrow(() -> new NoSuchElementException("Question not found with id: " + question.getId()));

            checkTextUnique(question);

            int maxPosition = questionRepository.getMaxPosition(question.getTestId());

            Integer requestedPosition = question.getPosition();

            if (!existing.getTestId().equals(question.getTestId())) {
                throw new IllegalArgumentException("Cannot move to another test");
            }

            int oldPosition = existing.getPosition();

            if (requestedPosition == null ||
                    requestedPosition.equals(oldPosition)) {

                existing.setText(question.getText());
                existing.setType(question.getType());

                return questionRepository.save(existing);
            }

            int newPosition = requestedPosition;

            if (newPosition <= 0) {
                newPosition = 1;
            }

            if (newPosition > maxPosition) {
                newPosition = maxPosition;
            }

            if (newPosition < oldPosition) {

                questionRepository.shiftForMoveUp(
                        question.getTestId(),
                        newPosition,
                        oldPosition
                );

            } else {

                questionRepository.shiftForMoveDown(
                        question.getTestId(),
                        oldPosition,
                        newPosition
                );
            }

            existing.setText(question.getText());
            existing.setType(question.getType());
            existing.setPosition(newPosition);

            return questionRepository.save(existing);
        }
    }

    @Override
    @Transactional
    public void deleteQuestion(UUID id) {
        Question questionToDelete = questionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Question not found with id: " + id));

        int position = questionToDelete.getPosition();
        UUID testId = questionToDelete.getTestId();

        questionRepository.deleteById(id);

        if (position != 0) {
            questionRepository.shiftUp(testId, position);
        }
    }

    private void checkTextUnique(Question question) {

        if (question.getText() == null ||
                question.getText().isBlank()) {
            return;
        }

        Question filter = new Question();
        filter.setTestId(question.getTestId());
        filter.setText(question.getText());

        List<Question> list =
                questionRepository.findByFilter(filter);

        boolean conflict = list.stream()
                .anyMatch(q ->
                        !q.getId().equals(question.getId())
                );

        if (conflict) {
            throw new IllegalArgumentException(
                    "Question text exists"
            );
        }
    }
}
