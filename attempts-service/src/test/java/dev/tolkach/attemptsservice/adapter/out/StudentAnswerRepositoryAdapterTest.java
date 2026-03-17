package dev.tolkach.attemptsservice.adapter.out;

import dev.tolkach.attemptsservice.adapter.out.persistence.entity.StudentAnswerEntity;
import dev.tolkach.attemptsservice.adapter.out.persistence.mapper.StudentAnswerMapper;
import dev.tolkach.attemptsservice.adapter.out.persistence.repository.JpaStudentAnswerRepository;
import dev.tolkach.attemptsservice.adapter.out.persistence.repository.StudentAnswerRepositoryAdapter;
import dev.tolkach.attemptsservice.application.model.ScaleScoreResult;
import dev.tolkach.attemptsservice.application.model.StudentAnswer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentAnswerRepositoryAdapterTest {

    @Mock
    JpaStudentAnswerRepository repository;

    @Mock
    StudentAnswerMapper mapper;

    @InjectMocks
    StudentAnswerRepositoryAdapter adapter;

    StudentAnswer domain;
    StudentAnswerEntity entity;
    UUID id;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        domain = new StudentAnswer();
        entity = new StudentAnswerEntity();
    }

    @Test
    void save_shouldReturnSavedDomain() {
        when(mapper.toEntity(domain)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(domain);

        StudentAnswer result = adapter.save(domain);

        assertEquals(domain, result);
    }

    @Test
    void findById_shouldReturnDomain() {
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        Optional<StudentAnswer> result = adapter.findById(id);

        assertTrue(result.isPresent());
        assertEquals(domain, result.get());
    }

    @Test
    void findById_shouldReturnEmpty() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        Optional<StudentAnswer> result = adapter.findById(id);

        assertTrue(result.isEmpty());
    }

    @Test
    void findByFilter_shouldReturnMappedList() {
        when(repository.findAll(ArgumentMatchers.<Specification<StudentAnswerEntity>>any())).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        List<StudentAnswer> result = adapter.findByFilter(domain);

        assertEquals(1, result.size());
    }

    @Test
    void deleteById_shouldCallRepository() {
        adapter.deleteById(id);

        verify(repository).deleteById(id);
    }

    @Test
    void calculateScoresWithInterpretation_shouldReturnResult() {
        List<ScaleScoreResult> expected = List.of(mock(ScaleScoreResult.class));

        when(repository.calculateScoresWithInterpretation(id)).thenReturn(expected);

        List<ScaleScoreResult> result = adapter.calculateScoresWithInterpretation(id);

        assertEquals(expected, result);
    }
}
