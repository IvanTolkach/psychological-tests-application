package dev.tolkach.methodologiesservice.adapter.in.rest.mapper;

import dev.tolkach.methodologiesservice.adapter.in.rest.dto.ScaleQuestionDto;
import dev.tolkach.methodologiesservice.application.model.ScaleQuestion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScaleQuestionDtoMapper {
    ScaleQuestion toEntity(ScaleQuestionDto dto);
    ScaleQuestionDto toDto(ScaleQuestion entity);
}
