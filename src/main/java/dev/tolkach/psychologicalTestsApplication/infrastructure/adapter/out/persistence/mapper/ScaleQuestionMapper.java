package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.mapper;

import dev.tolkach.psychologicalTestsApplication.domain.model.ScaleQuestion;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.entity.ScaleQuestionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScaleQuestionMapper {
    ScaleQuestionEntity toEntity(ScaleQuestion scaleQuestion);
    ScaleQuestion toDomain(ScaleQuestionEntity entity);
}
