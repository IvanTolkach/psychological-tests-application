package dev.tolkach.attemptsservice.service;

import dev.tolkach.attemptsservice.application.model.StudentAnswer;
import dev.tolkach.attemptsservice.application.model.TestAttempt;
import dev.tolkach.attemptsservice.application.port.out.StudentAnswerRepository;
import dev.tolkach.attemptsservice.application.port.out.TestAttemptRepository;
import dev.tolkach.attemptsservice.application.port.out.TestsPort;
import dev.tolkach.attemptsservice.application.service.StudentAnswerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentAnswerServiceTest {

    @Mock
    StudentAnswerRepository repository;

    @Mock
    TestAttemptRepository attemptRepository;

    @Mock
    TestsPort testsPort;

    @InjectMocks
    StudentAnswerService service;

    UUID id;
    UUID attemptId;
    UUID questionId;
    UUID optionId;

    StudentAnswer answer;

    @BeforeEach
    void setup() {

        id = UUID.randomUUID();
        attemptId = UUID.randomUUID();
        questionId = UUID.randomUUID();
        optionId = UUID.randomUUID();

        answer = new StudentAnswer();
        answer.setId(id);
        answer.setTestAttemptId(attemptId);
        answer.setQuestionId(questionId);
        answer.setAnswerOptionId(optionId);
    }

    @Test
    void getByFilter_allValidations() {

        StudentAnswer filter = new StudentAnswer();
        filter.setTestAttemptId(attemptId);
        filter.setQuestionId(questionId);
        filter.setAnswerOptionId(optionId);

        when(attemptRepository.findById(attemptId))
                .thenReturn(Optional.of(new TestAttempt()));

        when(repository.findByFilter(filter))
                .thenReturn(List.of(answer));

        List<StudentAnswer> result =
                service.getStudentAnswersByFilter(filter);

        assertEquals(1, result.size());

        verify(testsPort).validateQuestionExists(questionId);
        verify(testsPort).validateAnswerOptionExists(optionId);
    }

    @Test
    void create_new_success() {

        answer.setId(null);

        when(attemptRepository.findById(attemptId))
                .thenReturn(Optional.of(new TestAttempt()));

        when(repository.findByFilter(any()))
                .thenReturn(List.of());

        when(repository.save(answer))
                .thenReturn(answer);

        StudentAnswer result =
                service.createUpdateStudentAnswer(answer);

        assertEquals(answer, result);
    }

    @Test
    void create_duplicate_throws() {

        answer.setId(null);

        StudentAnswer existing = new StudentAnswer();
        existing.setId(UUID.randomUUID());

        when(attemptRepository.findById(attemptId))
                .thenReturn(Optional.of(new TestAttempt()));

        when(repository.findByFilter(any()))
                .thenReturn(List.of(existing));

        assertThrows(
                IllegalArgumentException.class,
                () -> service.createUpdateStudentAnswer(answer)
        );
    }

    @Test
    void update_success() {

        StudentAnswer existing = new StudentAnswer();
        existing.setId(id);

        when(attemptRepository.findById(attemptId))
                .thenReturn(Optional.of(new TestAttempt()));

        when(repository.findByFilter(any()))
                .thenReturn(List.of(existing));

        when(repository.findById(id))
                .thenReturn(Optional.of(existing));

        when(repository.save(existing))
                .thenReturn(existing);

        StudentAnswer result =
                service.createUpdateStudentAnswer(answer);

        assertEquals(existing, result);
    }

    @Test
    void update_notFound() {

        when(attemptRepository.findById(attemptId))
                .thenReturn(Optional.of(new TestAttempt()));

        when(repository.findByFilter(any()))
                .thenReturn(List.of());

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> service.createUpdateStudentAnswer(answer)
        );
    }

    @Test
    void delete_found() {

        when(repository.findById(id))
                .thenReturn(Optional.of(answer));

        service.deleteStudentAnswer(id);

        verify(repository).deleteById(id);
    }

    @Test
    void delete_notFound() {

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> service.deleteStudentAnswer(id)
        );
    }
}