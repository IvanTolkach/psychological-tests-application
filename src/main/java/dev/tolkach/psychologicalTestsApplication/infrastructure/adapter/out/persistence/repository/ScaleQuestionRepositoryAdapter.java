package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.repository;

import dev.tolkach.psychologicalTestsApplication.domain.model.ScaleQuestion;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.ScaleQuestionRepository;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.entity.ScaleQuestionEntity;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.mapper.ScaleQuestionMapper;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.specification.ScaleQuestionSpecification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ScaleQuestionRepositoryAdapter implements ScaleQuestionRepository {

    private final JpaScaleQuestionRepository jpaScaleQuestionRepository;
    private final ScaleQuestionMapper scaleQuestionMapper;

    public ScaleQuestionRepositoryAdapter(JpaScaleQuestionRepository jpaScaleQuestionRepository, ScaleQuestionMapper scaleQuestionMapper) {
        this.jpaScaleQuestionRepository = jpaScaleQuestionRepository;
        this.scaleQuestionMapper = scaleQuestionMapper;
    }

    @Override
    public ScaleQuestion save(ScaleQuestion scaleQuestion) {
        ScaleQuestionEntity entity = scaleQuestionMapper.toEntity(scaleQuestion);
        ScaleQuestionEntity saved = jpaScaleQuestionRepository.save(entity);
        return scaleQuestionMapper.toDomain(saved);
    }

    @Override
    public Optional<ScaleQuestion> findById(UUID id) {
        return jpaScaleQuestionRepository.findById(id)
                .map(scaleQuestionMapper::toDomain);
    }

    @Override
    public List<ScaleQuestion> findByFilter(ScaleQuestion filter) {
        return jpaScaleQuestionRepository.findAll(ScaleQuestionSpecification.filterBy(filter)).stream()
                .map(scaleQuestionMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaScaleQuestionRepository.deleteById(id);
    }
}