package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.mapper;

import dev.tolkach.psychologicalTestsApplication.domain.model.ScaleQuestion;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto.ScaleQuestionDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScaleQuestionDtoMapper {
    ScaleQuestion toEntity(ScaleQuestionDto dto);
    ScaleQuestionDto toDto(ScaleQuestion entity);
}
