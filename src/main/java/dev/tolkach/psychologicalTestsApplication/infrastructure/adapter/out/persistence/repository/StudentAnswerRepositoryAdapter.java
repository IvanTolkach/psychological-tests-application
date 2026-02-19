package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.repository;

import dev.tolkach.psychologicalTestsApplication.domain.model.StudentAnswer;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.StudentAnswerRepository;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.entity.StudentAnswerEntity;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.mapper.StudentAnswerMapper;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.specification.StudentAnswerSpecification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class StudentAnswerRepositoryAdapter implements StudentAnswerRepository {

    private final JpaStudentAnswerRepository jpaStudentAnswerRepository;
    private final StudentAnswerMapper studentAnswerMapper;

    public StudentAnswerRepositoryAdapter(JpaStudentAnswerRepository jpaStudentAnswerRepository, StudentAnswerMapper studentAnswerMapper) {
        this.jpaStudentAnswerRepository = jpaStudentAnswerRepository;
        this.studentAnswerMapper = studentAnswerMapper;
    }

    @Override
    public StudentAnswer save(StudentAnswer studentAnswer) {
        StudentAnswerEntity entity = studentAnswerMapper.toEntity(studentAnswer);
        StudentAnswerEntity saved = jpaStudentAnswerRepository.save(entity);
        return studentAnswerMapper.toDomain(saved);
    }

    @Override
    public Optional<StudentAnswer> findById(UUID id) {
        return jpaStudentAnswerRepository.findById(id)
                .map(studentAnswerMapper::toDomain);
    }

    @Override
    public List<StudentAnswer> findByFilter(StudentAnswer filter) {
        return jpaStudentAnswerRepository.findAll(StudentAnswerSpecification.filterBy(filter)).stream()
                .map(studentAnswerMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaStudentAnswerRepository.deleteById(id);
    }
}
