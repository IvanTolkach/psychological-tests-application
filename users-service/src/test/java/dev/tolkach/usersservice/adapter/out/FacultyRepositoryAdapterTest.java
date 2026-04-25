package dev.tolkach.usersservice.adapter.out;

import dev.tolkach.usersservice.adapter.out.persistence.entity.FacultyEntity;
import dev.tolkach.usersservice.adapter.out.persistence.mapper.FacultyMapper;
import dev.tolkach.usersservice.adapter.out.persistence.repository.FacultyRepositoryAdapter;
import dev.tolkach.usersservice.adapter.out.persistence.repository.JpaFacultyRepository;
import dev.tolkach.usersservice.application.model.Faculty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacultyRepositoryAdapterTest {

    @Mock
    JpaFacultyRepository jpaFacultyRepository;

    @Mock
    FacultyMapper facultyMapper;

    @InjectMocks
    FacultyRepositoryAdapter adapter;

    Faculty faculty;
    FacultyEntity entity;

    @BeforeEach
    void setup() {
        faculty = new Faculty();
        faculty.setId(UUID.randomUUID());

        entity = new FacultyEntity();
        entity.setId(faculty.getId());
    }

    @SuppressWarnings("unchecked")
    @Test
    void findByFilter_returnsList() {
        when(jpaFacultyRepository.findAll(any(Specification.class))).thenReturn(List.of(entity));
        when(facultyMapper.toDomain(entity)).thenReturn(faculty);

        List<Faculty> result = adapter.findByFilter(new Faculty());
        assertEquals(1, result.size());
        assertEquals(faculty, result.getFirst());
    }

    @Test
    void findById_present() {
        when(jpaFacultyRepository.findById(faculty.getId())).thenReturn(Optional.of(entity));
        when(facultyMapper.toDomain(entity)).thenReturn(faculty);

        Optional<Faculty> result = adapter.findById(faculty.getId());
        assertTrue(result.isPresent());
        assertEquals(faculty, result.get());
    }

    @Test
    void findById_empty() {
        when(jpaFacultyRepository.findById(faculty.getId())).thenReturn(Optional.empty());
        assertTrue(adapter.findById(faculty.getId()).isEmpty());
    }
}