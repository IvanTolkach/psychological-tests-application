package dev.tolkach.testsservice.adapter.out;

import dev.tolkach.testsservice.adapter.out.persistence.entity.QuestionEntity;
import dev.tolkach.testsservice.adapter.out.persistence.mapper.QuestionMapper;
import dev.tolkach.testsservice.adapter.out.persistence.repository.JpaQuestionRepository;
import dev.tolkach.testsservice.adapter.out.persistence.repository.QuestionRepositoryAdapter;
import dev.tolkach.testsservice.application.model.Question;
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
class QuestionRepositoryAdapterTest {

    @Mock
    JpaQuestionRepository jpaRepository;

    @Mock
    QuestionMapper mapper;

    @InjectMocks
    QuestionRepositoryAdapter adapter;

    Question domain;
    QuestionEntity entity;
    UUID id;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        domain = new Question();
        entity = new QuestionEntity();
    }

    @Test
    void save() {

        when(mapper.toEntity(domain)).thenReturn(entity);
        when(jpaRepository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(domain);

        Question result = adapter.save(domain);

        assertEquals(domain, result);
    }

    @Test
    void findById_present() {

        when(jpaRepository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        Optional<Question> result = adapter.findById(id);

        assertTrue(result.isPresent());
    }

    @Test
    void findById_empty() {

        when(jpaRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Question> result = adapter.findById(id);

        assertTrue(result.isEmpty());
    }

    @Test
    void findByFilter() {

        when(jpaRepository.findAll(
                ArgumentMatchers.<Specification<QuestionEntity>>any()))
                .thenReturn(List.of(entity));

        when(mapper.toDomain(entity)).thenReturn(domain);

        List<Question> result = adapter.findByFilter(domain);

        assertEquals(1, result.size());
    }

    @Test
    void deleteById() {

        adapter.deleteById(id);

        verify(jpaRepository).deleteById(id);
    }

    @Test
    void shiftDown() {

        adapter.shiftDown(id, 1);

        verify(jpaRepository).shiftDown(id, 1);
    }

    @Test
    void shiftUp() {

        adapter.shiftUp(id, 2);

        verify(jpaRepository).shiftUp(id, 2);
    }

    @Test
    void shiftForMoveUp() {

        adapter.shiftForMoveUp(id, 1, 3);

        verify(jpaRepository).shiftForMoveUp(id, 1, 3);
    }

    @Test
    void shiftForMoveDown() {

        adapter.shiftForMoveDown(id, 3, 1);

        verify(jpaRepository).shiftForMoveDown(id, 3, 1);
    }

    @Test
    void getMaxPosition() {

        when(jpaRepository.getMaxPosition(id)).thenReturn(5);

        int result = adapter.getMaxPosition(id);

        assertEquals(5, result);
    }
}
