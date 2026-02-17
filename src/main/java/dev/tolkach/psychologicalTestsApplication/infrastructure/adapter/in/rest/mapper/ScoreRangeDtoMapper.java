package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.mapper;

import dev.tolkach.psychologicalTestsApplication.domain.model.ScoreRange;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto.ScoreRangeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScoreRangeDtoMapper {
    ScoreRange toEntity(ScoreRangeDto dto);
    ScoreRangeDto toDto(ScoreRange entity);
}
