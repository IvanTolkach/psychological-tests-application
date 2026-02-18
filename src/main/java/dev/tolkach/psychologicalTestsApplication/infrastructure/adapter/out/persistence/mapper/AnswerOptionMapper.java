package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.mapper;

import dev.tolkach.psychologicalTestsApplication.domain.model.AnswerOption;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.entity.AnswerOptionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerOptionMapper {
    AnswerOptionEntity toEntity(AnswerOption answerOption);
    AnswerOption toDomain(AnswerOptionEntity entity);
}