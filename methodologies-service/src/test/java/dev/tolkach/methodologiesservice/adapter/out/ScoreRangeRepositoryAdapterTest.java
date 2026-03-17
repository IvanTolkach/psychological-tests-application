package dev.tolkach.methodologiesservice.adapter.out;

import dev.tolkach.methodologiesservice.adapter.out.persistence.entity.ScoreRangeEntity;
import dev.tolkach.methodologiesservice.adapter.out.persistence.mapper.ScoreRangeMapper;
import dev.tolkach.methodologiesservice.adapter.out.persistence.repository.JpaScoreRangeRepository;
import dev.tolkach.methodologiesservice.adapter.out.persistence.repository.ScoreRangeRepositoryAdapter;
import dev.tolkach.methodologiesservice.application.model.ScoreRange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScoreRangeRepositoryAdapterTest {

    @Mock
    JpaScoreRangeRepository jpaRepo;
    @Mock
    ScoreRangeMapper mapper;

    ScoreRangeRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new ScoreRangeRepositoryAdapter(jpaRepo, mapper);
    }

    @Test
    void save_returnsMapped() {
        ScoreRange domain = new ScoreRange();
        ScoreRangeEntity entity = new ScoreRangeEntity();
        ScoreRangeEntity saved = new ScoreRangeEntity();
        ScoreRange mappedBack = new ScoreRange();

        when(mapper.toEntity(domain)).thenReturn(entity);
        when(jpaRepo.save(entity)).thenReturn(saved);
        when(mapper.toDomain(saved)).thenReturn(mappedBack);

        assertEquals(mappedBack, adapter.save(domain));
    }

    @Test
    void findById_present() {
        UUID id = UUID.randomUUID();
        ScoreRange domain = new ScoreRange();
        ScoreRangeEntity entity = new ScoreRangeEntity();

        when(jpaRepo.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        assertEquals(Optional.of(domain), adapter.findById(id));
    }

    @Test
    void findById_empty() {
        UUID id = UUID.randomUUID();
        when(jpaRepo.findById(id)).thenReturn(Optional.empty());

        assertTrue(adapter.findById(id).isEmpty());
    }

    @SuppressWarnings("unchecked")
    @Test
    void findByFilter_returnsMappedList() {
        ScoreRange filter = new ScoreRange();
        ScoreRangeEntity entity = new ScoreRangeEntity();
        ScoreRange domain = new ScoreRange();

        when(jpaRepo.findAll(any(org.springframework.data.jpa.domain.Specification.class)))
                .thenReturn(List.of(entity));

        when(mapper.toDomain(entity)).thenReturn(domain);

        List<ScoreRange> result = adapter.findByFilter(filter);

        assertEquals(1, result.size());
        assertEquals(domain, result.getFirst());
    }

    @Test
    void deleteById_callsJpa() {
        UUID id = UUID.randomUUID();
        doNothing().when(jpaRepo).deleteById(id);

        adapter.deleteById(id);
        verify(jpaRepo).deleteById(id);
    }
}
