package dev.tolkach.methodologiesservice.adapter.out;

import dev.tolkach.methodologiesservice.adapter.out.persistence.entity.MethodologyEntity;
import dev.tolkach.methodologiesservice.adapter.out.persistence.mapper.MethodologyMapper;
import dev.tolkach.methodologiesservice.adapter.out.persistence.repository.JpaMethodologyRepository;
import dev.tolkach.methodologiesservice.adapter.out.persistence.repository.MethodologyRepositoryAdapter;
import dev.tolkach.methodologiesservice.application.model.Methodology;
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
class MethodologyRepositoryAdapterTest {

    @Mock
    private JpaMethodologyRepository jpaRepository;

    @Mock
    private MethodologyMapper mapper;

    @InjectMocks
    private MethodologyRepositoryAdapter adapter;

    private Methodology domain;
    private MethodologyEntity entity;
    private UUID id;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        domain = new Methodology();
        entity = new MethodologyEntity();
    }

    @Test
    void save_shouldReturnSavedDomain() {

        when(mapper.toEntity(domain)).thenReturn(entity);
        when(jpaRepository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(domain);

        Methodology result = adapter.save(domain);

        assertEquals(domain, result);
    }

    @Test
    void findById_shouldReturnDomain() {

        when(jpaRepository.findById(id))
                .thenReturn(Optional.of(entity));

        when(mapper.toDomain(entity))
                .thenReturn(domain);

        Optional<Methodology> result = adapter.findById(id);

        assertTrue(result.isPresent());
        assertEquals(domain, result.get());
    }

    @Test
    void findById_shouldReturnEmpty() {

        when(jpaRepository.findById(id))
                .thenReturn(Optional.empty());

        Optional<Methodology> result = adapter.findById(id);

        assertTrue(result.isEmpty());
    }

    @Test
    void findByFilter_shouldReturnMappedList() {

        when(jpaRepository.findAll(ArgumentMatchers.<Specification<MethodologyEntity>>any()))
                .thenReturn(List.of(entity));

        when(mapper.toDomain(entity))
                .thenReturn(domain);

        List<Methodology> result = adapter.findByFilter(domain);

        assertEquals(1, result.size());
    }

    @Test
    void deleteById_shouldCallRepository() {

        adapter.deleteById(id);

        verify(jpaRepository).deleteById(id);
    }
}