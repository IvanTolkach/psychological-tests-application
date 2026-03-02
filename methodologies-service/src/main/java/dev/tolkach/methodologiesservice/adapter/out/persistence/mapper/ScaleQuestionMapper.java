package dev.tolkach.methodologiesservice.adapter.out.persistence.mapper;

import dev.tolkach.methodologiesservice.adapter.out.persistence.entity.ScaleQuestionEntity;
import dev.tolkach.methodologiesservice.application.model.ScaleQuestion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScaleQuestionMapper {
    ScaleQuestionEntity toEntity(ScaleQuestion scaleQuestion);
    ScaleQuestion toDomain(ScaleQuestionEntity entity);
}
