package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.mapper;

import dev.tolkach.psychologicalTestsApplication.domain.model.Methodology;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto.MethodologyDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MethodologyDtoMapper {
    Methodology toEntity(MethodologyDto dto);
    MethodologyDto toDto(Methodology entity);
}