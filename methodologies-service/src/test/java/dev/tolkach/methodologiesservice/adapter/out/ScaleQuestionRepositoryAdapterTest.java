package dev.tolkach.methodologiesservice.adapter.out;

import dev.tolkach.methodologiesservice.adapter.out.persistence.entity.ScaleQuestionEntity;
import dev.tolkach.methodologiesservice.adapter.out.persistence.mapper.ScaleQuestionMapper;
import dev.tolkach.methodologiesservice.adapter.out.persistence.repository.JpaScaleQuestionRepository;
import dev.tolkach.methodologiesservice.adapter.out.persistence.repository.ScaleQuestionRepositoryAdapter;
import dev.tolkach.methodologiesservice.application.model.ScaleQuestion;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScaleQuestionRepositoryAdapterTest {

    @Mock
    private JpaScaleQuestionRepository jpaRepository;

    @Mock
    private ScaleQuestionMapper mapper;

    @InjectMocks
    private ScaleQuestionRepositoryAdapter adapter;

    private ScaleQuestion domain;
    private ScaleQuestionEntity entity;
    private UUID id;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        domain = new ScaleQuestion();
        entity = new ScaleQuestionEntity();
    }

    @Test
    void save_shouldReturnSavedDomain() {

        when(mapper.toEntity(domain)).thenReturn(entity);
        when(jpaRepository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(domain);

        ScaleQuestion result = adapter.save(domain);

        assertEquals(domain, result);
    }

    @Test
    void findById_shouldReturnDomain() {

        when(jpaRepository.findById(id))
                .thenReturn(Optional.of(entity));

        when(mapper.toDomain(entity))
                .thenReturn(domain);

        Optional<ScaleQuestion> result = adapter.findById(id);

        assertTrue(result.isPresent());
        assertEquals(domain, result.get());
    }

    @Test
    void findById_shouldReturnEmpty() {

        when(jpaRepository.findById(id))
                .thenReturn(Optional.empty());

        Optional<ScaleQuestion> result = adapter.findById(id);

        assertTrue(result.isEmpty());
    }

    @Test
    void findByFilter_shouldReturnMappedList() {

        when(jpaRepository.findAll(ArgumentMatchers.<Specification<ScaleQuestionEntity>>any()))
                .thenReturn(List.of(entity));

        when(mapper.toDomain(entity))
                .thenReturn(domain);

        List<ScaleQuestion> result = adapter.findByFilter(domain);

        assertEquals(1, result.size());
        assertEquals(domain, result.getFirst());
    }

    @Test
    void deleteById_shouldCallRepository() {

        adapter.deleteById(id);

        verify(jpaRepository).deleteById(id);
    }
}
