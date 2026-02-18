package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.repository;

import dev.tolkach.psychologicalTestsApplication.domain.model.Test;
import dev.tolkach.psychologicalTestsApplication.domain.model.TestFilter;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.TestRepository;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.entity.TestEntity;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.mapper.TestMapper;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.specification.TestSpecification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TestRepositoryAdapter implements TestRepository {

    private final JpaTestRepository jpaTestRepository;
    private final TestMapper testMapper;

    public TestRepositoryAdapter(JpaTestRepository jpaTestRepository, TestMapper testMapper) {
        this.jpaTestRepository = jpaTestRepository;
        this.testMapper = testMapper;
    }

    @Override
    public Test save(Test test) {
        TestEntity entity = testMapper.toEntity(test);
        TestEntity saved = jpaTestRepository.save(entity);
        return testMapper.toDomain(saved);
    }

    @Override
    public Optional<Test> findById(UUID id) {
        return jpaTestRepository.findById(id)
                .map(testMapper::toDomain);
    }

    @Override
    public List<Test> findByFilter(TestFilter filter) {
        return jpaTestRepository.findAll(TestSpecification.filterBy(filter)).stream()
                .map(testMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaTestRepository.deleteById(id);
    }
}
