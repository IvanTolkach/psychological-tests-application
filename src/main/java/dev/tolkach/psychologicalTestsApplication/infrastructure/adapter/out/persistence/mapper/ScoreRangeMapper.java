package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.mapper;

import dev.tolkach.psychologicalTestsApplication.domain.model.ScoreRange;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.entity.ScoreRangeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScoreRangeMapper {
    ScoreRangeEntity toEntity(ScoreRange scoreRange);
    ScoreRange toDomain(ScoreRangeEntity entity);
}
