package dev.tolkach.testsservice.adapter.out;

import dev.tolkach.testsservice.adapter.out.persistence.entity.TestEntity;
import dev.tolkach.testsservice.adapter.out.persistence.mapper.TestMapper;
import dev.tolkach.testsservice.adapter.out.persistence.repository.JpaTestRepository;
import dev.tolkach.testsservice.adapter.out.persistence.repository.TestRepositoryAdapter;
import dev.tolkach.testsservice.application.model.TestFilter;
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
class TestRepositoryAdapterTest {

    @Mock
    JpaTestRepository jpaRepository;

    @Mock
    TestMapper mapper;

    @InjectMocks
    TestRepositoryAdapter adapter;

    dev.tolkach.testsservice.application.model.Test domain;
    TestEntity entity;
    UUID id;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        domain = new dev.tolkach.testsservice.application.model.Test();
        entity = new TestEntity();
    }

    @Test
    void save() {

        when(mapper.toEntity(domain)).thenReturn(entity);
        when(jpaRepository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(domain);

        dev.tolkach.testsservice.application.model.Test result = adapter.save(domain);

        assertEquals(domain, result);
    }

    @Test
    void findById_present() {

        when(jpaRepository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        Optional<dev.tolkach.testsservice.application.model.Test> result = adapter.findById(id);

        assertTrue(result.isPresent());
    }

    @Test
    void findById_empty() {

        when(jpaRepository.findById(id)).thenReturn(Optional.empty());

        Optional<dev.tolkach.testsservice.application.model.Test> result = adapter.findById(id);

        assertTrue(result.isEmpty());
    }

    @Test
    void findByFilter() {

        when(jpaRepository.findAll(
                ArgumentMatchers.<Specification<TestEntity>>any()))
                .thenReturn(List.of(entity));

        when(mapper.toDomain(entity)).thenReturn(domain);

        List<dev.tolkach.testsservice.application.model.Test> result = adapter.findByFilter(new TestFilter());

        assertEquals(1, result.size());
    }

    @Test
    void deleteById() {

        adapter.deleteById(id);

        verify(jpaRepository).deleteById(id);
    }
}
