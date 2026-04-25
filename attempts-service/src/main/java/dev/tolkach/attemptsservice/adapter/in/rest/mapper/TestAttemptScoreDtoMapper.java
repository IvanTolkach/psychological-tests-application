package dev.tolkach.attemptsservice.adapter.in.rest.mapper;

import dev.tolkach.attemptsservice.adapter.in.rest.dto.TestAttemptScoreDto;
import dev.tolkach.attemptsservice.application.model.TestAttemptScore;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestAttemptScoreDtoMapper {
    TestAttemptScore toEntity(TestAttemptScoreDto dto);
    TestAttemptScoreDto toDto(TestAttemptScore entity);
}