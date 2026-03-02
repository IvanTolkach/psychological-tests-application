package dev.tolkach.methodologiesservice.adapter.out.persistence.mapper;

import dev.tolkach.methodologiesservice.adapter.out.persistence.entity.ScoreRangeEntity;
import dev.tolkach.methodologiesservice.application.model.ScoreRange;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScoreRangeMapper {
    ScoreRangeEntity toEntity(ScoreRange scoreRange);
    ScoreRange toDomain(ScoreRangeEntity entity);
}
