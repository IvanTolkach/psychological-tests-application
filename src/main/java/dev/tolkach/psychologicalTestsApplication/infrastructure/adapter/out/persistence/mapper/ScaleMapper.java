package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.mapper;

import dev.tolkach.psychologicalTestsApplication.domain.model.Scale;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.entity.ScaleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScaleMapper {
    ScaleEntity toEntity(Scale scale);
    Scale toDomain(ScaleEntity entity);
}
