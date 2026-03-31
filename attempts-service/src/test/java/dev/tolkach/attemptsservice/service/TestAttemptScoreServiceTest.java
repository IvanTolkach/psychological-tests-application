package dev.tolkach.attemptsservice.service;

import dev.tolkach.attemptsservice.application.model.ScaleScoreResult;
import dev.tolkach.attemptsservice.application.model.TestAttempt;
import dev.tolkach.attemptsservice.application.model.TestAttemptScore;
import dev.tolkach.attemptsservice.application.port.out.MethodologiesPort;
import dev.tolkach.attemptsservice.application.port.out.StudentAnswerRepository;
import dev.tolkach.attemptsservice.application.port.out.TestAttemptRepository;
import dev.tolkach.attemptsservice.application.port.out.TestAttemptScoreRepository;
import dev.tolkach.attemptsservice.application.service.TestAttemptScoreService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestAttemptScoreServiceTest {

    @Mock
    TestAttemptScoreRepository repo;

    @Mock
    TestAttemptRepository attemptRepo;

    @Mock
    MethodologiesPort methodologiesPort;

    @Mock
    StudentAnswerRepository answerRepo;

    @InjectMocks
    TestAttemptScoreService service;

    UUID attemptId;
    UUID scoreId;
    UUID scaleId;

    TestAttemptScore score;

    @BeforeEach
    void setup() {

        attemptId = UUID.randomUUID();
        scoreId = UUID.randomUUID();
        scaleId = UUID.randomUUID();

        score = new TestAttemptScore();
        score.setId(scoreId);
        score.setTestAttemptId(attemptId);
        score.setScaleId(scaleId);
    }

    @Test
    void getByFilter_validations() {

        TestAttemptScore filter = new TestAttemptScore();
        filter.setTestAttemptId(attemptId);
        filter.setScaleId(scaleId);

        when(attemptRepo.findById(attemptId))
                .thenReturn(Optional.of(new TestAttempt()));

        when(repo.findByFilter(filter))
                .thenReturn(List.of(score));

        List<TestAttemptScore> result =
                service.getTestAttemptScoresByFilter(filter);

        assertEquals(1, result.size());

        verify(methodologiesPort)
                .validateScaleExists(scaleId);
    }

    @Test
    void create_attemptNull() {

        TestAttemptScore s = new TestAttemptScore();

        assertThrows(
                IllegalArgumentException.class,
                () -> service.createUpdateTestAttemptScore(s)
        );
    }

    @Test
    void create_attemptNotFound() {

        TestAttemptScore s = new TestAttemptScore();
        s.setTestAttemptId(attemptId);

        when(attemptRepo.findById(attemptId))
                .thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> service.createUpdateTestAttemptScore(s)
        );
    }

    @Test
    void create_noScores() {

        TestAttemptScore s = new TestAttemptScore();
        s.setTestAttemptId(attemptId);

        when(attemptRepo.findById(attemptId))
                .thenReturn(Optional.of(new TestAttempt()));

        when(answerRepo.calculateScoresWithInterpretation(attemptId))
                .thenReturn(List.of());

        assertThrows(
                IllegalArgumentException.class,
                () -> service.createUpdateTestAttemptScore(s)
        );
    }

    @Test
    void create_newScores_saved() {

        TestAttemptScore s = new TestAttemptScore();
        s.setTestAttemptId(attemptId);

        ScaleScoreResult r = mock(ScaleScoreResult.class);

        when(r.getScaleId()).thenReturn(scaleId);
        when(r.getScore()).thenReturn(5);
        when(r.getInterpretation()).thenReturn("ok");

        when(attemptRepo.findById(attemptId))
                .thenReturn(Optional.of(new TestAttempt()));

        when(answerRepo.calculateScoresWithInterpretation(attemptId))
                .thenReturn(List.of(r));

        when(repo.findByFilter(any()))
                .thenReturn(List.of());

        when(repo.saveAll(any()))
                .thenAnswer(i -> i.getArgument(0));

        List<TestAttemptScore> result =
                service.createUpdateTestAttemptScore(s);

        assertEquals(1, result.size());
    }

    @Test
    void create_existingReturned() {

        TestAttemptScore s = new TestAttemptScore();
        s.setTestAttemptId(attemptId);

        ScaleScoreResult r = mock(ScaleScoreResult.class);

        when(r.getScaleId()).thenReturn(scaleId);

        when(attemptRepo.findById(attemptId))
                .thenReturn(Optional.of(new TestAttempt()));

        when(answerRepo.calculateScoresWithInterpretation(attemptId))
                .thenReturn(List.of(r));

        when(repo.findByFilter(any()))
                .thenReturn(List.of(score));

        List<TestAttemptScore> result =
                service.createUpdateTestAttemptScore(s);

        assertEquals(1, result.size());
    }

    @Test
    void update_success() {

        score.setScore(1);

        when(attemptRepo.findById(attemptId))
                .thenReturn(Optional.of(new TestAttempt()));

        when(repo.findById(scoreId))
                .thenReturn(Optional.of(score));

        when(repo.save(score))
                .thenReturn(score);

        List<TestAttemptScore> result =
                service.createUpdateTestAttemptScore(score);

        assertEquals(1, result.size());
    }

    @Test
    void update_notFound() {

        when(attemptRepo.findById(attemptId))
                .thenReturn(Optional.of(new TestAttempt()));

        when(repo.findById(scoreId))
                .thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> service.createUpdateTestAttemptScore(score)
        );
    }

    @Test
    void update_withInterpretation_updatesInterpretation() {

        score.setScore(1);
        score.setInterpretation("new");

        TestAttemptScore existing = new TestAttemptScore();
        existing.setId(scoreId);
        existing.setTestAttemptId(attemptId);
        existing.setScaleId(scaleId);

        when(attemptRepo.findById(attemptId))
                .thenReturn(Optional.of(new TestAttempt()));

        when(repo.findById(scoreId))
                .thenReturn(Optional.of(existing));

        when(repo.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        List<TestAttemptScore> result =
                service.createUpdateTestAttemptScore(score);

        assertEquals("new", result.getFirst().getInterpretation());
    }

    @Test
    void delete_found() {

        when(repo.findById(scoreId))
                .thenReturn(Optional.of(score));

        service.deleteTestAttemptScore(scoreId);

        verify(repo).deleteById(scoreId);
    }

    @Test
    void delete_notFound() {

        when(repo.findById(scoreId))
                .thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> service.deleteTestAttemptScore(scoreId)
        );
    }
}