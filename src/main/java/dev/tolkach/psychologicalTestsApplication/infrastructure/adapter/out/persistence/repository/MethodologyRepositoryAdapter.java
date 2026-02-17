package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.repository;

import dev.tolkach.psychologicalTestsApplication.domain.model.Methodology;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.MethodologyRepository;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.entity.MethodologyEntity;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.mapper.MethodologyMapper;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.specification.MethodologySpecification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class MethodologyRepositoryAdapter implements MethodologyRepository {

    private final JpaMethodologyRepository jpaMethodologyRepository;
    private final MethodologyMapper methodologyMapper;

    public MethodologyRepositoryAdapter(JpaMethodologyRepository jpaMethodologyRepository, MethodologyMapper methodologyMapper) {
        this.jpaMethodologyRepository = jpaMethodologyRepository;
        this.methodologyMapper = methodologyMapper;
    }

    @Override
    public Methodology save(Methodology methodology) {
        MethodologyEntity entity = methodologyMapper.toEntity(methodology);
        MethodologyEntity saved = jpaMethodologyRepository.save(entity);
        return methodologyMapper.toDomain(saved);
    }

    @Override
    public Optional<Methodology> findById(UUID id) {
        return jpaMethodologyRepository.findById(id)
                .map(methodologyMapper::toDomain);
    }

    @Override
    public List<Methodology> findByFilter(Methodology filter) {
        return jpaMethodologyRepository.findAll(MethodologySpecification.filterBy(filter)).stream()
                .map(methodologyMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaMethodologyRepository.deleteById(id);
    }
}
