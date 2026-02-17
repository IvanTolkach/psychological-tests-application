package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.repository;

import dev.tolkach.psychologicalTestsApplication.domain.model.Scale;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.ScaleRepository;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.entity.ScaleEntity;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.mapper.ScaleMapper;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.specification.ScaleSpecification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ScaleRepositoryAdapter implements ScaleRepository {

    private final JpaScaleRepository jpaScaleRepository;
    private final ScaleMapper scaleMapper;

    public ScaleRepositoryAdapter(JpaScaleRepository jpaScaleRepository, ScaleMapper scaleMapper) {
        this.jpaScaleRepository = jpaScaleRepository;
        this.scaleMapper = scaleMapper;
    }

    @Override
    public Scale save(Scale scale) {
        ScaleEntity entity = scaleMapper.toEntity(scale);
        ScaleEntity saved = jpaScaleRepository.save(entity);
        return scaleMapper.toDomain(saved);
    }

    @Override
    public Optional<Scale> findById(UUID id) {
        return jpaScaleRepository.findById(id)
                .map(scaleMapper::toDomain);
    }

    @Override
    public List<Scale> findByFilter(Scale filter) {
        return jpaScaleRepository.findAll(ScaleSpecification.filterBy(filter)).stream()
                .map(scaleMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaScaleRepository.deleteById(id);
    }
}
