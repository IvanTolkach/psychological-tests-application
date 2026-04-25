package dev.tolkach.methodologiesservice.adapter.out.persistence.repository;

import dev.tolkach.methodologiesservice.adapter.out.persistence.entity.ScoreRangeEntity;
import dev.tolkach.methodologiesservice.adapter.out.persistence.mapper.ScoreRangeMapper;
import dev.tolkach.methodologiesservice.adapter.out.persistence.specification.ScoreRangeSpecification;
import dev.tolkach.methodologiesservice.application.model.ScoreRange;
import dev.tolkach.methodologiesservice.application.port.out.ScoreRangeRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ScoreRangeRepositoryAdapter implements ScoreRangeRepository {

    private final JpaScoreRangeRepository jpaScoreRangeRepository;
    private final ScoreRangeMapper scoreRangeMapper;

    public ScoreRangeRepositoryAdapter(JpaScoreRangeRepository jpaScoreRangeRepository, ScoreRangeMapper scoreRangeMapper) {
        this.jpaScoreRangeRepository = jpaScoreRangeRepository;
        this.scoreRangeMapper = scoreRangeMapper;
    }

    @Override
    public ScoreRange save(ScoreRange scoreRange) {
        ScoreRangeEntity entity = scoreRangeMapper.toEntity(scoreRange);
        ScoreRangeEntity saved = jpaScoreRangeRepository.save(entity);
        return scoreRangeMapper.toDomain(saved);
    }

    @Override
    public Optional<ScoreRange> findById(UUID id) {
        return jpaScoreRangeRepository.findById(id)
                .map(scoreRangeMapper::toDomain);
    }

    @Override
    public List<ScoreRange> findByFilter(ScoreRange filter) {
        return jpaScoreRangeRepository.findAll(ScoreRangeSpecification.filterBy(filter)).stream()
                .map(scoreRangeMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaScoreRangeRepository.deleteById(id);
    }
}
