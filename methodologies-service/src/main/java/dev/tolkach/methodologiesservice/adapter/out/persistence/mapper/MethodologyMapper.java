package dev.tolkach.methodologiesservice.adapter.out.persistence.mapper;

import dev.tolkach.methodologiesservice.adapter.out.persistence.entity.MethodologyEntity;
import dev.tolkach.methodologiesservice.application.model.Methodology;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MethodologyMapper {
    MethodologyEntity toEntity(Methodology methodology);
    Methodology toDomain(MethodologyEntity entity);
}
