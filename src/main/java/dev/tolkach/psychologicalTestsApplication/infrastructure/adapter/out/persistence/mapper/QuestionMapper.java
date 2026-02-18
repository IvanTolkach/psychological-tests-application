package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.mapper;

import dev.tolkach.psychologicalTestsApplication.domain.model.Question;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.entity.QuestionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    QuestionEntity toEntity(Question question);
    Question toDomain(QuestionEntity entity);
}
