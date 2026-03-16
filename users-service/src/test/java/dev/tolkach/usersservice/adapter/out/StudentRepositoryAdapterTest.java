package dev.tolkach.usersservice.adapter.out;

import dev.tolkach.usersservice.adapter.out.persistence.entity.StudentEntity;
import dev.tolkach.usersservice.adapter.out.persistence.mapper.StudentMapper;
import dev.tolkach.usersservice.adapter.out.persistence.repository.JpaStudentRepository;
import dev.tolkach.usersservice.adapter.out.persistence.repository.StudentRepositoryAdapter;
import dev.tolkach.usersservice.application.model.Student;
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
class StudentRepositoryAdapterTest {

    @Mock
    JpaStudentRepository jpaStudentRepository;

    @Mock
    StudentMapper studentMapper;

    @InjectMocks
    StudentRepositoryAdapter adapter;

    Student student;
    StudentEntity entity;

    @BeforeEach
    void setup() {
        student = new Student();
        student.setId(UUID.randomUUID());

        entity = new StudentEntity();
        entity.setId(student.getId());
    }

    @Test
    void save_success() {
        when(studentMapper.toEntity(student)).thenReturn(entity);
        when(jpaStudentRepository.save(entity)).thenReturn(entity);
        when(studentMapper.toDomain(entity)).thenReturn(student);

        Student result = adapter.save(student);
        assertEquals(student, result);
    }

    @Test
    void findById_present() {
        when(jpaStudentRepository.findById(student.getId())).thenReturn(Optional.of(entity));
        when(studentMapper.toDomain(entity)).thenReturn(student);

        Optional<Student> result = adapter.findById(student.getId());
        assertTrue(result.isPresent());
    }

    @Test
    void findById_empty() {
        when(jpaStudentRepository.findById(student.getId())).thenReturn(Optional.empty());
        assertTrue(adapter.findById(student.getId()).isEmpty());
    }

    @Test
    void findByFilter_returnsList() {
        when(jpaStudentRepository.findAll(any(Specification.class))).thenReturn(List.of(entity));
        when(studentMapper.toDomain(entity)).thenReturn(student);

        List<Student> result = adapter.findByFilter(new Student());
        assertEquals(1, result.size());
    }

    @Test
    void deleteById_callsRepository() {
        adapter.deleteById(student.getId());
        verify(jpaStudentRepository).deleteById(student.getId());
    }
}
