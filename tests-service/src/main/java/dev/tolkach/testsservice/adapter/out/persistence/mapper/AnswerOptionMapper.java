package dev.tolkach.testsservice.adapter.out.persistence.mapper;

import dev.tolkach.testsservice.adapter.out.persistence.entity.AnswerOptionEntity;
import dev.tolkach.testsservice.application.model.AnswerOption;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerOptionMapper {
    AnswerOptionEntity toEntity(AnswerOption answerOption);
    AnswerOption toDomain(AnswerOptionEntity entity);
}