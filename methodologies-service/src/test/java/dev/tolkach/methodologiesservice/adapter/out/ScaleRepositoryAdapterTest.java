package dev.tolkach.methodologiesservice.adapter.out;

import dev.tolkach.methodologiesservice.adapter.out.persistence.entity.ScaleEntity;
import dev.tolkach.methodologiesservice.adapter.out.persistence.mapper.ScaleMapper;
import dev.tolkach.methodologiesservice.adapter.out.persistence.repository.JpaScaleRepository;
import dev.tolkach.methodologiesservice.adapter.out.persistence.repository.ScaleRepositoryAdapter;
import dev.tolkach.methodologiesservice.application.model.Scale;
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
class ScaleRepositoryAdapterTest {

    @Mock
    private JpaScaleRepository jpaScaleRepository;

    @Mock
    private ScaleMapper scaleMapper;

    @InjectMocks
    private ScaleRepositoryAdapter adapter;

    private Scale scale;
    private ScaleEntity entity;
    private UUID id;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        scale = new Scale();
        entity = new ScaleEntity();
    }

    @Test
    void save_shouldReturnSavedDomain() {

        when(scaleMapper.toEntity(scale)).thenReturn(entity);
        when(jpaScaleRepository.save(entity)).thenReturn(entity);
        when(scaleMapper.toDomain(entity)).thenReturn(scale);

        Scale result = adapter.save(scale);

        assertEquals(scale, result);

        verify(scaleMapper).toEntity(scale);
        verify(jpaScaleRepository).save(entity);
        verify(scaleMapper).toDomain(entity);
    }

    @Test
    void findById_shouldReturnDomain_whenFound() {

        when(jpaScaleRepository.findById(id))
                .thenReturn(Optional.of(entity));

        when(scaleMapper.toDomain(entity))
                .thenReturn(scale);

        Optional<Scale> result = adapter.findById(id);

        assertTrue(result.isPresent());
        assertEquals(scale, result.get());
    }

    @Test
    void findById_shouldReturnEmpty_whenNotFound() {

        when(jpaScaleRepository.findById(id))
                .thenReturn(Optional.empty());

        Optional<Scale> result = adapter.findById(id);

        assertTrue(result.isEmpty());
    }

    @Test
    void findByFilter_shouldReturnMappedList() {

        List<ScaleEntity> entities = List.of(entity);

        when(jpaScaleRepository.findAll(ArgumentMatchers.<Specification<ScaleEntity>>any()))
                .thenReturn(entities);

        when(scaleMapper.toDomain(entity))
                .thenReturn(scale);

        List<Scale> result = adapter.findByFilter(scale);

        assertEquals(1, result.size());
        assertEquals(scale, result.getFirst());
    }

    @Test
    void deleteById_shouldCallRepository() {

        adapter.deleteById(id);

        verify(jpaScaleRepository).deleteById(id);
    }
}