package dev.tolkach.testsservice.adapter.in.rest.mapper;

import dev.tolkach.testsservice.adapter.in.rest.dto.QuestionDto;
import dev.tolkach.testsservice.application.model.Question;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionDtoMapper {
    Question toEntity(QuestionDto dto);
    QuestionDto toDto(Question entity);
}
