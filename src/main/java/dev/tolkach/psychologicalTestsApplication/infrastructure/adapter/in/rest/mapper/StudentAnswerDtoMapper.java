package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.mapper;

import dev.tolkach.psychologicalTestsApplication.domain.model.StudentAnswer;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto.StudentAnswerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentAnswerDtoMapper {
    StudentAnswer toEntity(StudentAnswerDto dto);
    StudentAnswerDto toDto(StudentAnswer entity);
}
