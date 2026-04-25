package dev.tolkach.testsservice.adapter.out.persistence.mapper;

import dev.tolkach.testsservice.adapter.out.persistence.entity.QuestionEntity;
import dev.tolkach.testsservice.application.model.Question;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    QuestionEntity toEntity(Question question);
    Question toDomain(QuestionEntity entity);
}
