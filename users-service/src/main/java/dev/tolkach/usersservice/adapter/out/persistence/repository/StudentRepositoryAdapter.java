package dev.tolkach.usersservice.adapter.out.persistence.repository;

import dev.tolkach.usersservice.adapter.out.persistence.entity.StudentEntity;
import dev.tolkach.usersservice.adapter.out.persistence.mapper.StudentMapper;
import dev.tolkach.usersservice.adapter.out.persistence.specification.StudentSpecification;
import dev.tolkach.usersservice.application.model.Student;
import dev.tolkach.usersservice.application.port.out.StudentRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class StudentRepositoryAdapter implements StudentRepository {
    private final JpaStudentRepository jpaStudentRepository;
    private final StudentMapper studentMapper;

    public StudentRepositoryAdapter(JpaStudentRepository jpaStudentRepository, StudentMapper studentMapper) {
        this.jpaStudentRepository = jpaStudentRepository;
        this.studentMapper = studentMapper;
    }

    @Override
    public Student save(Student student) {
        StudentEntity entity = studentMapper.toEntity(student);
        StudentEntity saved = jpaStudentRepository.save(entity);
        return studentMapper.toDomain(saved);
    }

    @Override
    public Optional<Student> findById(UUID id) {
        return jpaStudentRepository.findById(id)
                .map(studentMapper::toDomain);
    }

    @Override
    public List<Student> findByFilter(Student filter) {
        return jpaStudentRepository.findAll(StudentSpecification.filterBy(filter)).stream()
                .map(studentMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaStudentRepository.deleteById(id);
    }
}
