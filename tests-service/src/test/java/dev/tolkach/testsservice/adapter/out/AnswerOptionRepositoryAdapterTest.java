package dev.tolkach.testsservice.adapter.out;

import dev.tolkach.testsservice.adapter.out.persistence.entity.AnswerOptionEntity;
import dev.tolkach.testsservice.adapter.out.persistence.mapper.AnswerOptionMapper;
import dev.tolkach.testsservice.adapter.out.persistence.repository.AnswerOptionRepositoryAdapter;
import dev.tolkach.testsservice.adapter.out.persistence.repository.JpaAnswerOptionRepository;
import dev.tolkach.testsservice.application.model.AnswerOption;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnswerOptionRepositoryAdapterTest {

    @Mock
    JpaAnswerOptionRepository jpaRepository;

    @Mock
    AnswerOptionMapper mapper;

    @InjectMocks
    AnswerOptionRepositoryAdapter adapter;

    AnswerOption domain;
    AnswerOptionEntity entity;
    UUID id;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        domain = new AnswerOption();
        entity = new AnswerOptionEntity();
    }

    @Test
    void save_shouldReturnSavedDomain() {

        when(mapper.toEntity(domain)).thenReturn(entity);
        when(jpaRepository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(domain);

        AnswerOption result = adapter.save(domain);

        assertEquals(domain, result);
    }

    @Test
    void findById_shouldReturnDomain() {

        when(jpaRepository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        Optional<AnswerOption> result = adapter.findById(id);

        assertTrue(result.isPresent());
        assertEquals(domain, result.get());
    }

    @Test
    void findById_shouldReturnEmpty() {

        when(jpaRepository.findById(id)).thenReturn(Optional.empty());

        Optional<AnswerOption> result = adapter.findById(id);

        assertTrue(result.isEmpty());
    }

    @Test
    void findByFilter_shouldReturnMappedList() {

        when(jpaRepository.findAll(
                ArgumentMatchers.<Specification<AnswerOptionEntity>>any()))
                .thenReturn(List.of(entity));

        when(mapper.toDomain(entity)).thenReturn(domain);

        List<AnswerOption> result = adapter.findByFilter(domain);

        assertEquals(1, result.size());
    }

    @Test
    void deleteById_shouldCallRepository() {

        adapter.deleteById(id);

        verify(jpaRepository).deleteById(id);
    }
}
