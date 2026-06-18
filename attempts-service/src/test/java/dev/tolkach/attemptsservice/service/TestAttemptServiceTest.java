package dev.tolkach.attemptsservice.service;

import common.dto.StudentDto;
import dev.tolkach.attemptsservice.application.exception.DuplicateTestAttemptException;
import dev.tolkach.attemptsservice.application.model.TestAttempt;
import dev.tolkach.attemptsservice.application.model.TestAttemptFilter;
import dev.tolkach.attemptsservice.application.port.out.TestAttemptRepository;
import dev.tolkach.attemptsservice.application.port.out.TestsPort;
import dev.tolkach.attemptsservice.application.port.out.UsersPort;
import dev.tolkach.attemptsservice.application.service.TestAttemptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestAttemptServiceTest {

    @Mock
    TestAttemptRepository repository;

    @Mock
    UsersPort usersPort;

    @Mock
    TestsPort testsPort;

    @InjectMocks
    TestAttemptService service;

    UUID id;
    UUID studentId;
    UUID testId;

    TestAttempt attempt;
    StudentDto student;

    @BeforeEach
    void setup() {

        id = UUID.randomUUID();
        studentId = UUID.randomUUID();
        testId = UUID.randomUUID();

        attempt = new TestAttempt();
        attempt.setId(id);
        attempt.setStudentId(studentId);
        attempt.setTestId(testId);

        student = new StudentDto();
        student.setId(studentId);
        student.setGroupNumber(10701122);

        lenient().when(usersPort.getStudentById(studentId))
                .thenReturn(student);
        lenient().when(repository.existsByStudentAndTestInPeriod(any(), any(), any(), any(), any()))
                .thenReturn(false);
    }

    @Test
    void getByFilter_validations() {

        TestAttemptFilter filter = new TestAttemptFilter();
        filter.setStudentId(studentId);
        filter.setTestId(testId);

        when(repository.findByFilter(filter))
                .thenReturn(List.of(attempt));

        List<TestAttempt> result =
                service.getTestAttemptsByFilter(filter);

        assertEquals(1, result.size());

        verify(usersPort).validateStudentExists(studentId);
        verify(testsPort).validateTestExists(testId);
    }

    @Test
    void create_new_attempt_setsDate() {

        attempt.setId(null);
        attempt.setAttemptDate(null);

        when(repository.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        TestAttempt result =
                service.createUpdateTestAttempt(attempt);

        assertNotNull(result.getAttemptDate());
    }

    @Test
    void create_firstCourseAttempt_checksAcademicYearPeriod() {

        attempt.setId(null);
        attempt.setAttemptDate(LocalDateTime.of(2023, 8, 31, 23, 59));

        when(repository.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        service.createUpdateTestAttempt(attempt);

        verify(repository).existsByStudentAndTestInPeriod(
                eq(studentId),
                eq(testId),
                eq(LocalDateTime.of(2022, 9, 1, 0, 0)),
                eq(LocalDateTime.of(2023, 9, 1, 0, 0)),
                eq(null)
        );
    }

    @Test
    void create_duplicateAttemptInSameCourse_throws() {

        attempt.setId(null);
        attempt.setAttemptDate(LocalDateTime.of(2022, 9, 1, 0, 0));

        when(repository.existsByStudentAndTestInPeriod(any(), any(), any(), any(), any()))
                .thenReturn(true);

        assertThrows(
                DuplicateTestAttemptException.class,
                () -> service.createUpdateTestAttempt(attempt)
        );
    }

    @Test
    void create_attemptBeforeAdmission_throws() {

        attempt.setId(null);
        attempt.setAttemptDate(LocalDateTime.of(2022, 8, 31, 23, 59));

        assertThrows(
                IllegalArgumentException.class,
                () -> service.createUpdateTestAttempt(attempt)
        );
    }

    @Test
    void create_futureDate_throws() {

        attempt.setId(null);
        attempt.setAttemptDate(LocalDateTime.now().plusDays(1));

        assertThrows(
                IllegalArgumentException.class,
                () -> service.createUpdateTestAttempt(attempt)
        );
    }

    @Test
    void update_existing() {

        LocalDateTime date = LocalDateTime.now().minusDays(1);
        attempt.setAttemptDate(date);

        TestAttempt existing = new TestAttempt();
        existing.setId(id);

        when(repository.findById(id))
                .thenReturn(Optional.of(existing));

        when(repository.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        TestAttempt result =
                service.createUpdateTestAttempt(attempt);

        assertEquals(date, result.getAttemptDate());
        verify(repository).existsByStudentAndTestInPeriod(any(), any(), any(), any(), eq(id));
    }

    @Test
    void update_notFound() {

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> service.createUpdateTestAttempt(attempt)
        );
    }

    @Test
    void delete_found() {

        when(repository.findById(id))
                .thenReturn(Optional.of(attempt));

        service.deleteTestAttempt(id);

        verify(repository).deleteById(id);
    }

    @Test
    void delete_notFound() {

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> service.deleteTestAttempt(id)
        );
    }
}
