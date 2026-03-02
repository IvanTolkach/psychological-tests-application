package dev.tolkach.methodologiesservice.adapter.out.persistence.repository;

import dev.tolkach.methodologiesservice.adapter.out.persistence.entity.ScaleEntity;
import dev.tolkach.methodologiesservice.adapter.out.persistence.mapper.ScaleMapper;
import dev.tolkach.methodologiesservice.adapter.out.persistence.specification.ScaleSpecification;
import dev.tolkach.methodologiesservice.application.model.Scale;
import dev.tolkach.methodologiesservice.application.port.out.ScaleRepository;
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
