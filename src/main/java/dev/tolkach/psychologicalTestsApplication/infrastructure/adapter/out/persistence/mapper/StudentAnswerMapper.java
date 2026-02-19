package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.mapper;

import dev.tolkach.psychologicalTestsApplication.domain.model.StudentAnswer;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.entity.StudentAnswerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentAnswerMapper {
    StudentAnswerEntity toEntity(StudentAnswer studentAnswer);
    StudentAnswer toDomain(StudentAnswerEntity entity);
}