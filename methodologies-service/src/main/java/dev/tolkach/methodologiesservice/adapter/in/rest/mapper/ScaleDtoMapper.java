package dev.tolkach.methodologiesservice.adapter.in.rest.mapper;

import dev.tolkach.methodologiesservice.adapter.in.rest.dto.ScaleDto;
import dev.tolkach.methodologiesservice.application.model.Scale;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScaleDtoMapper {
    Scale toEntity(ScaleDto dto);
    ScaleDto toDto(Scale entity);
}
