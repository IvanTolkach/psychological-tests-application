package dev.tolkach.methodologiesservice.adapter.out.persistence.mapper;

import dev.tolkach.methodologiesservice.adapter.out.persistence.entity.ScaleEntity;
import dev.tolkach.methodologiesservice.application.model.Scale;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScaleMapper {
    ScaleEntity toEntity(Scale scale);
    Scale toDomain(ScaleEntity entity);
}
