package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.mapper;

import dev.tolkach.psychologicalTestsApplication.domain.model.Question;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto.QuestionDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionDtoMapper {
    Question toEntity(QuestionDto dto);
    QuestionDto toDto(Question entity);
}
