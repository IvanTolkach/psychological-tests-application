package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.repository;

import dev.tolkach.psychologicalTestsApplication.domain.model.TestAttempt;
import dev.tolkach.psychologicalTestsApplication.domain.model.TestAttemptFilter;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.TestAttemptRepository;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.entity.TestAttemptEntity;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.mapper.TestAttemptMapper;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.specification.TestAttemptSpecification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TestAttemptRepositoryAdapter implements TestAttemptRepository {

    private final JpaTestAttemptRepository jpaTestAttemptRepository;
    private final TestAttemptMapper testAttemptMapper;

    public TestAttemptRepositoryAdapter(JpaTestAttemptRepository jpaTestAttemptRepository, TestAttemptMapper testAttemptMapper) {
        this.jpaTestAttemptRepository = jpaTestAttemptRepository;
        this.testAttemptMapper = testAttemptMapper;
    }

    @Override
    public TestAttempt save(TestAttempt testAttempt) {
        TestAttemptEntity entity = testAttemptMapper.toEntity(testAttempt);
        TestAttemptEntity saved = jpaTestAttemptRepository.save(entity);
        return testAttemptMapper.toDomain(saved);
    }

    @Override
    public Optional<TestAttempt> findById(UUID id) {
        return jpaTestAttemptRepository.findById(id)
                .map(testAttemptMapper::toDomain);
    }

    @Override
    public List<TestAttempt> findByFilter(TestAttemptFilter filter) {
        return jpaTestAttemptRepository.findAll(TestAttemptSpecification.filterBy(filter)).stream()
                .map(testAttemptMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaTestAttemptRepository.deleteById(id);
    }
}