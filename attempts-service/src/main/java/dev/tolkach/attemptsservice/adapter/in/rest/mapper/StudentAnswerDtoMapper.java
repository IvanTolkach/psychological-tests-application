package dev.tolkach.attemptsservice.adapter.in.rest.mapper;

import dev.tolkach.attemptsservice.adapter.in.rest.dto.StudentAnswerDto;
import dev.tolkach.attemptsservice.application.model.StudentAnswer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentAnswerDtoMapper {
    StudentAnswer toEntity(StudentAnswerDto dto);
    StudentAnswerDto toDto(StudentAnswer entity);
}
