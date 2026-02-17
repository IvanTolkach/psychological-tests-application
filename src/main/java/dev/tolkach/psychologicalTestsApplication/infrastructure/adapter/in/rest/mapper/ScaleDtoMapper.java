package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.mapper;

import dev.tolkach.psychologicalTestsApplication.domain.model.Scale;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto.ScaleDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScaleDtoMapper {
    Scale toEntity(ScaleDto dto);
    ScaleDto toDto(Scale entity);
}
