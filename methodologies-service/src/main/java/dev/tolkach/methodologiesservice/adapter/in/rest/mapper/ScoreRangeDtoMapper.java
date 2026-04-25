package dev.tolkach.methodologiesservice.adapter.in.rest.mapper;

import dev.tolkach.methodologiesservice.adapter.in.rest.dto.ScoreRangeDto;
import dev.tolkach.methodologiesservice.application.model.ScoreRange;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScoreRangeDtoMapper {
    ScoreRange toEntity(ScoreRangeDto dto);
    ScoreRangeDto toDto(ScoreRange entity);
}
