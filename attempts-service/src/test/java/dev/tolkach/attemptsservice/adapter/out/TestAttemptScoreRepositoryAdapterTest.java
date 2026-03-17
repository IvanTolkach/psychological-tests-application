package dev.tolkach.attemptsservice.adapter.out;

import dev.tolkach.attemptsservice.adapter.out.persistence.entity.TestAttemptScoreEntity;
import dev.tolkach.attemptsservice.adapter.out.persistence.mapper.TestAttemptScoreMapper;
import dev.tolkach.attemptsservice.adapter.out.persistence.repository.JpaTestAttemptScoreRepository;
import dev.tolkach.attemptsservice.adapter.out.persistence.repository.TestAttemptScoreRepositoryAdapter;
import dev.tolkach.attemptsservice.application.model.TestAttemptScore;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestAttemptScoreRepositoryAdapterTest {

    @Mock
    JpaTestAttemptScoreRepository repository;

    @Mock
    TestAttemptScoreMapper mapper;

    @InjectMocks
    TestAttemptScoreRepositoryAdapter adapter;

    TestAttemptScore domain;
    TestAttemptScoreEntity entity;
    UUID id;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        domain = new TestAttemptScore();
        entity = new TestAttemptScoreEntity();
    }

    @Test
    void save_shouldReturnSavedDomain() {
        when(mapper.toEntity(domain)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(domain);

        assertEquals(domain, adapter.save(domain));
    }

    @Test
    void saveAll_shouldReturnMappedList() {
        List<TestAttemptScore> list = List.of(domain);

        when(mapper.toEntity(domain)).thenReturn(entity);
        when(repository.saveAll(any())).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        List<TestAttemptScore> result = adapter.saveAll(list);

        assertEquals(1, result.size());
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
        when(repository.findAll(ArgumentMatchers.<Specification<TestAttemptScoreEntity>>any())).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        List<TestAttemptScore> result = adapter.findByFilter(domain);

        assertEquals(1, result.size());
    }

    @Test
    void deleteById_shouldCallRepository() {
        adapter.deleteById(id);

        verify(repository).deleteById(id);
    }
}
