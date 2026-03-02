package dev.tolkach.testsservice.adapter.in.rest.mapper;

import dev.tolkach.testsservice.adapter.in.rest.dto.AnswerOptionDto;
import dev.tolkach.testsservice.application.model.AnswerOption;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerOptionDtoMapper {
    AnswerOption toEntity(AnswerOptionDto dto);
    AnswerOptionDto toDto(AnswerOption entity);
}
