package dev.tolkach.attemptsservice.adapter.out;

import dev.tolkach.attemptsservice.adapter.out.persistence.entity.TestAttemptEntity;
import dev.tolkach.attemptsservice.adapter.out.persistence.mapper.TestAttemptMapper;
import dev.tolkach.attemptsservice.adapter.out.persistence.repository.JpaTestAttemptRepository;
import dev.tolkach.attemptsservice.adapter.out.persistence.repository.TestAttemptRepositoryAdapter;
import dev.tolkach.attemptsservice.application.model.TestAttempt;
import dev.tolkach.attemptsservice.application.model.TestAttemptFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestAttemptRepositoryAdapterTest {

    @Mock
    JpaTestAttemptRepository repository;

    @Mock
    TestAttemptMapper mapper;

    @InjectMocks
    TestAttemptRepositoryAdapter adapter;

    TestAttempt domain;
    TestAttemptEntity entity;
    UUID id;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        domain = new TestAttempt();
        entity = new TestAttemptEntity();
    }

    @Test
    void save_shouldReturnSavedDomain() {
        when(mapper.toEntity(domain)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(domain);

        assertEquals(domain, adapter.save(domain));
    }

    @Test
    void findById_shouldReturnDomain() {
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        assertTrue(adapter.findById(id).isPresent());
    }

    @Test
    void findById_shouldReturnEmpty() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertTrue(adapter.findById(id).isEmpty());
    }

    @Test
    void findByFilter_shouldReturnMappedList() {
        when(repository.findAll( ArgumentMatchers.<Specification<TestAttemptEntity>>any())).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        List<TestAttempt> result = adapter.findByFilter(new TestAttemptFilter());

        assertEquals(1, result.size());
    }

    @Test
    void existsByStudentAndTestInPeriod_withoutExcludedId_shouldCallRepository() {
        UUID studentId = UUID.randomUUID();
        UUID testId = UUID.randomUUID();
        LocalDateTime from = LocalDateTime.of(2022, 9, 1, 0, 0);
        LocalDateTime to = LocalDateTime.of(2023, 9, 1, 0, 0);

        when(repository.existsByStudentIdAndTestIdAndAttemptDateGreaterThanEqualAndAttemptDateLessThan(studentId, testId, from, to))
                .thenReturn(true);

        assertTrue(adapter.existsByStudentAndTestInPeriod(studentId, testId, from, to, null));
    }

    @Test
    void existsByStudentAndTestInPeriod_withExcludedId_shouldCallRepository() {
        UUID studentId = UUID.randomUUID();
        UUID testId = UUID.randomUUID();
        UUID excludedId = UUID.randomUUID();
        LocalDateTime from = LocalDateTime.of(2022, 9, 1, 0, 0);
        LocalDateTime to = LocalDateTime.of(2023, 9, 1, 0, 0);

        when(repository.existsByStudentIdAndTestIdAndAttemptDateGreaterThanEqualAndAttemptDateLessThanAndIdNot(studentId, testId, from, to, excludedId))
                .thenReturn(true);

        assertTrue(adapter.existsByStudentAndTestInPeriod(studentId, testId, from, to, excludedId));
    }

    @Test
    void deleteById_shouldCallRepository() {
        adapter.deleteById(id);

        verify(repository).deleteById(id);
    }
}
