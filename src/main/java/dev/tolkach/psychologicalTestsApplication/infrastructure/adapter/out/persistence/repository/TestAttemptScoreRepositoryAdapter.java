package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.repository;

import dev.tolkach.psychologicalTestsApplication.domain.model.TestAttemptScore;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.TestAttemptScoreRepository;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.entity.TestAttemptScoreEntity;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.mapper.TestAttemptScoreMapper;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.specification.TestAttemptScoreSpecification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TestAttemptScoreRepositoryAdapter implements TestAttemptScoreRepository {

    private final JpaTestAttemptScoreRepository jpaTestAttemptScoreRepository;
    private final TestAttemptScoreMapper testAttemptScoreMapper;

    public TestAttemptScoreRepositoryAdapter(JpaTestAttemptScoreRepository jpaTestAttemptScoreRepository, TestAttemptScoreMapper testAttemptScoreMapper) {
        this.jpaTestAttemptScoreRepository = jpaTestAttemptScoreRepository;
        this.testAttemptScoreMapper = testAttemptScoreMapper;
    }

    @Override
    public TestAttemptScore save(TestAttemptScore testAttemptScore) {
        TestAttemptScoreEntity entity = testAttemptScoreMapper.toEntity(testAttemptScore);
        TestAttemptScoreEntity saved = jpaTestAttemptScoreRepository.save(entity);
        return testAttemptScoreMapper.toDomain(saved);
    }

    @Override
    public Optional<TestAttemptScore> findById(UUID id) {
        return jpaTestAttemptScoreRepository.findById(id)
                .map(testAttemptScoreMapper::toDomain);
    }

    @Override
    public List<TestAttemptScore> findByFilter(TestAttemptScore filter) {
        return jpaTestAttemptScoreRepository.findAll(TestAttemptScoreSpecification.filterBy(filter)).stream()
                .map(testAttemptScoreMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaTestAttemptScoreRepository.deleteById(id);
    }
}
