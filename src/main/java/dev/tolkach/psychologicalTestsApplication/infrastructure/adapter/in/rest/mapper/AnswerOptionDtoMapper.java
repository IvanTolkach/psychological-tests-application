package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.mapper;

import dev.tolkach.psychologicalTestsApplication.domain.model.AnswerOption;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto.AnswerOptionDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerOptionDtoMapper {
    AnswerOption toEntity(AnswerOptionDto dto);
    AnswerOptionDto toDto(AnswerOption entity);
}
