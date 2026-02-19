package dev.tolkach.psychologicalTestsApplication.application.service;

import dev.tolkach.psychologicalTestsApplication.domain.model.TestAttempt;
import dev.tolkach.psychologicalTestsApplication.domain.model.TestAttemptFilter;
import dev.tolkach.psychologicalTestsApplication.domain.port.in.TestAttemptUseCase;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.StudentRepository;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.TestAttemptRepository;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.TestRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class TestAttemptService implements TestAttemptUseCase {

    private final TestAttemptRepository testAttemptRepository;
    private final StudentRepository studentRepository;
    private final TestRepository testRepository;

    public TestAttemptService(TestAttemptRepository testAttemptRepository, StudentRepository studentRepository, TestRepository testRepository) {
        this.testAttemptRepository = testAttemptRepository;
        this.studentRepository = studentRepository;
        this.testRepository = testRepository;
    }

    @Override
    public List<TestAttempt> getTestAttemptsByFilter(TestAttemptFilter filter) {
        if (filter.getStudentId() != null) {
            studentRepository.findById(filter.getStudentId())
                    .orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + filter.getStudentId()));
        }
        if (filter.getTestId() != null) {
            testRepository.findById(filter.getTestId())
                    .orElseThrow(() -> new IllegalArgumentException("Test not found with id: " + filter.getTestId()));
        }
        return testAttemptRepository.findByFilter(filter);
    }

    @Override
    public TestAttempt createUpdateTestAttempt(TestAttempt testAttempt) {
        studentRepository.findById(testAttempt.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + testAttempt.getStudentId()));

        testRepository.findById(testAttempt.getTestId())
                .orElseThrow(() -> new IllegalArgumentException("Test not found with id: " + testAttempt.getTestId()));

        LocalDateTime now = LocalDateTime.now();

        if (testAttempt.getAttemptDate() == null) {
            testAttempt.setAttemptDate(now);
        } else if (testAttempt.getAttemptDate().isAfter(now)) {
            throw new IllegalArgumentException("Attempt date cannot be in the future: " + testAttempt.getAttemptDate());
        }

        if (testAttempt.getId() == null) {
            return testAttemptRepository.save(testAttempt);
        } else {
            TestAttempt existing = testAttemptRepository.findById(testAttempt.getId())
                    .orElseThrow(() -> new IllegalArgumentException("TestAttempt not found with id: " + testAttempt.getId()));

            existing.setAttemptDate(testAttempt.getAttemptDate());

            return testAttemptRepository.save(existing);
        }
    }

    @Override
    public void deleteTestAttempt(UUID id) {
        if (testAttemptRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("TestAttempt not found with id: " + id);
        }
        testAttemptRepository.deleteById(id);
    }
}
