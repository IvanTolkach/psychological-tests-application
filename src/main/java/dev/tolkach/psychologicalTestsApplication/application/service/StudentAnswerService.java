package dev.tolkach.psychologicalTestsApplication.application.service;

import dev.tolkach.psychologicalTestsApplication.domain.model.StudentAnswer;
import dev.tolkach.psychologicalTestsApplication.domain.port.in.StudentAnswerUseCase;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.AnswerOptionRepository;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.QuestionRepository;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.StudentAnswerRepository;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.TestAttemptRepository;

import java.util.List;
import java.util.UUID;

public class StudentAnswerService implements StudentAnswerUseCase {

    private final StudentAnswerRepository studentAnswerRepository;
    private final TestAttemptRepository testAttemptRepository;
    private final QuestionRepository questionRepository;
    private final AnswerOptionRepository answerOptionRepository;

    public StudentAnswerService(StudentAnswerRepository studentAnswerRepository, TestAttemptRepository testAttemptRepository, QuestionRepository questionRepository, AnswerOptionRepository answerOptionRepository) {
        this.studentAnswerRepository = studentAnswerRepository;
        this.testAttemptRepository = testAttemptRepository;
        this.questionRepository = questionRepository;
        this.answerOptionRepository = answerOptionRepository;
    }

    @Override
    public List<StudentAnswer> getStudentAnswersByFilter(StudentAnswer filter) {
        if (filter.getTestAttemptId() != null) {
            testAttemptRepository.findById(filter.getTestAttemptId())
                    .orElseThrow(() -> new IllegalArgumentException("TestAttempt not found with id: " + filter.getTestAttemptId()));
        }
        if (filter.getQuestionId() != null) {
            questionRepository.findById(filter.getQuestionId())
                    .orElseThrow(() -> new IllegalArgumentException("Question not found with id: " + filter.getQuestionId()));
        }
        if (filter.getAnswerOptionId() != null) {
            answerOptionRepository.findById(filter.getAnswerOptionId())
                    .orElseThrow(() -> new IllegalArgumentException("AnswerOption not found with id: " + filter.getAnswerOptionId()));
        }
        return studentAnswerRepository.findByFilter(filter);
    }

    @Override
    public StudentAnswer createUpdateStudentAnswer(StudentAnswer studentAnswer) {
        testAttemptRepository.findById(studentAnswer.getTestAttemptId())
                .orElseThrow(() -> new IllegalArgumentException("TestAttempt not found with id: " + studentAnswer.getTestAttemptId()));

        questionRepository.findById(studentAnswer.getQuestionId())
                .orElseThrow(() -> new IllegalArgumentException("Question not found with id: " + studentAnswer.getQuestionId()));

        if (studentAnswer.getAnswerOptionId() != null) {
            answerOptionRepository.findById(studentAnswer.getAnswerOptionId())
                    .orElseThrow(() -> new IllegalArgumentException("AnswerOption not found with id: " + studentAnswer.getAnswerOptionId()));
        }

        StudentAnswer checkFilter = new StudentAnswer();
        checkFilter.setTestAttemptId(studentAnswer.getTestAttemptId());
        checkFilter.setQuestionId(studentAnswer.getQuestionId());

        List<StudentAnswer> existingAnswers = studentAnswerRepository.findByFilter(checkFilter);

        boolean alreadyAnswered = existingAnswers.stream()
                .anyMatch(sa -> !sa.getId().equals(studentAnswer.getId()));

        if (alreadyAnswered) {
            throw new IllegalArgumentException(
                    "Student already answered question " + studentAnswer.getQuestionId() +
                            " in attempt " + studentAnswer.getTestAttemptId()
            );
        }

        if (studentAnswer.getId() == null) {
            return studentAnswerRepository.save(studentAnswer);
        } else {
            StudentAnswer existing = studentAnswerRepository.findById(studentAnswer.getId())
                    .orElseThrow(() -> new IllegalArgumentException("StudentAnswer not found with id: " + studentAnswer.getId()));

            existing.setAnswerOptionId(studentAnswer.getAnswerOptionId());
            existing.setAnswerValue(studentAnswer.getAnswerValue());

            return studentAnswerRepository.save(existing);
        }
    }

    @Override
    public void deleteStudentAnswer(UUID id) {
        if (studentAnswerRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("StudentAnswer not found with id: " + id);
        }
        studentAnswerRepository.deleteById(id);
    }
}
