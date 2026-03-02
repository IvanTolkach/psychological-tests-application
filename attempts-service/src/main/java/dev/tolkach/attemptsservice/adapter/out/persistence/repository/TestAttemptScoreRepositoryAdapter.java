package dev.tolkach.attemptsservice.adapter.out.persistence.repository;

import dev.tolkach.attemptsservice.adapter.out.persistence.entity.TestAttemptScoreEntity;
import dev.tolkach.attemptsservice.adapter.out.persistence.mapper.TestAttemptScoreMapper;
import dev.tolkach.attemptsservice.adapter.out.persistence.specification.TestAttemptScoreSpecification;
import dev.tolkach.attemptsservice.application.model.TestAttemptScore;
import dev.tolkach.attemptsservice.application.port.out.TestAttemptScoreRepository;
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
