package dev.tolkach.methodologiesservice.adapter.in.rest.mapper;

import dev.tolkach.methodologiesservice.adapter.in.rest.dto.MethodologyDto;
import dev.tolkach.methodologiesservice.application.model.Methodology;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MethodologyDtoMapper {
    Methodology toEntity(MethodologyDto dto);
    MethodologyDto toDto(Methodology entity);
}