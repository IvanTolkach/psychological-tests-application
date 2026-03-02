package dev.tolkach.attemptsservice.application.service;

import dev.tolkach.attemptsservice.application.model.StudentAnswer;
import dev.tolkach.attemptsservice.application.port.in.StudentAnswerUseCase;
import dev.tolkach.attemptsservice.application.port.out.StudentAnswerRepository;
import dev.tolkach.attemptsservice.application.port.out.TestAttemptRepository;
import dev.tolkach.attemptsservice.application.port.out.TestsPort;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class StudentAnswerService implements StudentAnswerUseCase {

    private final StudentAnswerRepository studentAnswerRepository;
    private final TestAttemptRepository testAttemptRepository;
    private final TestsPort testsPort;

    public StudentAnswerService(StudentAnswerRepository studentAnswerRepository, TestAttemptRepository testAttemptRepository, TestsPort testsPort) {
        this.studentAnswerRepository = studentAnswerRepository;
        this.testAttemptRepository = testAttemptRepository;
        this.testsPort = testsPort;
    }

    @Override
    public List<StudentAnswer> getStudentAnswersByFilter(StudentAnswer filter) {
        if (filter.getTestAttemptId() != null) {
            testAttemptRepository.findById(filter.getTestAttemptId())
                    .orElseThrow(() -> new NoSuchElementException("TestAttempt not found with id: " + filter.getTestAttemptId()));
        }
        if (filter.getQuestionId() != null) {
            testsPort.validateQuestionExists(filter.getQuestionId());
        }
        if (filter.getAnswerOptionId() != null) {
            testsPort.validateAnswerOptionExists(filter.getAnswerOptionId());
        }
        return studentAnswerRepository.findByFilter(filter);
    }

    @Override
    public StudentAnswer createUpdateStudentAnswer(StudentAnswer studentAnswer) {
        testAttemptRepository.findById(studentAnswer.getTestAttemptId())
                .orElseThrow(() -> new NoSuchElementException("TestAttempt not found with id: " + studentAnswer.getTestAttemptId()));

        testsPort.validateQuestionExists(studentAnswer.getQuestionId());

        if (studentAnswer.getAnswerOptionId() != null) {
            testsPort.validateAnswerOptionExists(studentAnswer.getAnswerOptionId());
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
                    .orElseThrow(() -> new NoSuchElementException("StudentAnswer not found with id: " + studentAnswer.getId()));

            existing.setAnswerOptionId(studentAnswer.getAnswerOptionId());
            existing.setAnswerValue(studentAnswer.getAnswerValue());

            return studentAnswerRepository.save(existing);
        }
    }

    @Override
    public void deleteStudentAnswer(UUID id) {
        if (studentAnswerRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException("StudentAnswer not found with id: " + id);
        }
        studentAnswerRepository.deleteById(id);
    }
}
