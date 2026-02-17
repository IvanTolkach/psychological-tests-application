package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.mapper;

import dev.tolkach.psychologicalTestsApplication.domain.model.Methodology;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.entity.MethodologyEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MethodologyMapper {
    MethodologyEntity toEntity(Methodology methodology);
    Methodology toDomain(MethodologyEntity entity);
}
