package dev.tolkach.attemptsservice.application.service;

import dev.tolkach.attemptsservice.application.model.TestAttempt;
import dev.tolkach.attemptsservice.application.model.TestAttemptFilter;
import dev.tolkach.attemptsservice.application.port.in.TestAttemptUseCase;
import dev.tolkach.attemptsservice.application.port.out.TestAttemptRepository;
import dev.tolkach.attemptsservice.application.port.out.TestsPort;
import dev.tolkach.attemptsservice.application.port.out.UsersPort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class TestAttemptService implements TestAttemptUseCase {

    private final TestAttemptRepository testAttemptRepository;
    private final UsersPort usersPort;
    private final TestsPort testsPort;

    public TestAttemptService(TestAttemptRepository testAttemptRepository, UsersPort usersPort, TestsPort testsPort) {
        this.testAttemptRepository = testAttemptRepository;
        this.usersPort = usersPort;
        this.testsPort = testsPort;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestAttempt> getTestAttemptsByFilter(TestAttemptFilter filter) {
        if (filter.getStudentId() != null) {
            usersPort.validateStudentExists(filter.getStudentId());
        }
        if (filter.getTestId() != null) {
            testsPort.validateTestExists(filter.getTestId());
        }
        return testAttemptRepository.findByFilter(filter);
    }

    @Override
    @Transactional
    public TestAttempt createUpdateTestAttempt(TestAttempt testAttempt) {
        usersPort.validateStudentExists(testAttempt.getStudentId());

        testsPort.validateTestExists(testAttempt.getTestId());

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
                    .orElseThrow(() -> new NoSuchElementException("TestAttempt not found with id: " + testAttempt.getId()));

            existing.setAttemptDate(testAttempt.getAttemptDate());

            return testAttemptRepository.save(existing);
        }
    }

    @Override
    @Transactional
    public void deleteTestAttempt(UUID id) {
        if (testAttemptRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException("TestAttempt not found with id: " + id);
        }
        testAttemptRepository.deleteById(id);
    }
}
