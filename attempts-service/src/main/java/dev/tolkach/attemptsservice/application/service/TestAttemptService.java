package dev.tolkach.attemptsservice.application.service;

import common.dto.StudentDto;
import dev.tolkach.attemptsservice.application.exception.DuplicateTestAttemptException;
import dev.tolkach.attemptsservice.application.model.TestAttempt;
import dev.tolkach.attemptsservice.application.model.TestAttemptFilter;
import dev.tolkach.attemptsservice.application.port.in.TestAttemptUseCase;
import dev.tolkach.attemptsservice.application.port.out.TestAttemptRepository;
import dev.tolkach.attemptsservice.application.port.out.TestsPort;
import dev.tolkach.attemptsservice.application.port.out.UsersPort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
        StudentDto student = usersPort.getStudentById(testAttempt.getStudentId());

        testsPort.validateTestExists(testAttempt.getTestId());

        LocalDateTime now = LocalDateTime.now();

        if (testAttempt.getAttemptDate() == null) {
            testAttempt.setAttemptDate(now);
        } else if (testAttempt.getAttemptDate().isAfter(now)) {
            throw new IllegalArgumentException("Attempt date cannot be in the future: " + testAttempt.getAttemptDate());
        }

        if (testAttempt.getId() == null) {
            validateSingleAttemptPerCourse(testAttempt, student);
            return testAttemptRepository.save(testAttempt);
        } else {
            TestAttempt existing = testAttemptRepository.findById(testAttempt.getId())
                    .orElseThrow(() -> new NoSuchElementException("TestAttempt not found with id: " + testAttempt.getId()));

            validateSingleAttemptPerCourse(testAttempt, student);

            existing.setStudentId(testAttempt.getStudentId());
            existing.setTestId(testAttempt.getTestId());
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

    private void validateSingleAttemptPerCourse(TestAttempt testAttempt, StudentDto student) {
        if (student.getGroupNumber() == null) {
            throw new IllegalArgumentException("Student group number is required to validate course attempts");
        }

        int admissionYear = resolveAdmissionYear(student.getGroupNumber(), testAttempt.getAttemptDate().getYear());
        LocalDate academicYearStart = getAcademicYearStart(testAttempt.getAttemptDate().toLocalDate());

        if (academicYearStart.getYear() < admissionYear) {
            throw new IllegalArgumentException("Attempt date cannot be before student's admission date: " + LocalDate.of(admissionYear, 9, 1));
        }

        LocalDateTime fromInclusive = academicYearStart.atStartOfDay();
        LocalDateTime toExclusive = academicYearStart.plusYears(1).atStartOfDay();

        boolean duplicateExists = testAttemptRepository.existsByStudentAndTestInPeriod(
                testAttempt.getStudentId(),
                testAttempt.getTestId(),
                fromInclusive,
                toExclusive,
                testAttempt.getId()
        );

        if (duplicateExists) {
            int course = academicYearStart.getYear() - admissionYear + 1;
            throw new DuplicateTestAttemptException(
                    "Student already has an attempt for test " + testAttempt.getTestId() + " during course " + course
            );
        }
    }

    private int resolveAdmissionYear(Integer groupNumber, int referenceYear) {
        int admissionYearLastTwoDigits = Math.abs(groupNumber) % 100;
        int century = referenceYear / 100 * 100;
        int admissionYear = century + admissionYearLastTwoDigits;

        if (admissionYear > referenceYear) {
            admissionYear -= 100;
        }

        return admissionYear;
    }

    private LocalDate getAcademicYearStart(LocalDate date) {
        if (date.getMonthValue() >= 9) {
            return LocalDate.of(date.getYear(), 9, 1);
        }
        return LocalDate.of(date.getYear() - 1, 9, 1);
    }
}
