package dev.tolkach.attemptsservice.adapter.out.persistence.mapper;

import dev.tolkach.attemptsservice.adapter.out.persistence.entity.StudentAnswerEntity;
import dev.tolkach.attemptsservice.application.model.StudentAnswer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentAnswerMapper {
    StudentAnswerEntity toEntity(StudentAnswer studentAnswer);
    StudentAnswer toDomain(StudentAnswerEntity entity);
}