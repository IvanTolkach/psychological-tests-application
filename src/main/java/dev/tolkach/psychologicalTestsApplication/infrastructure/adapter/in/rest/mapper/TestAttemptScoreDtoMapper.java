package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.mapper;

import dev.tolkach.psychologicalTestsApplication.domain.model.TestAttemptScore;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto.TestAttemptScoreDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestAttemptScoreDtoMapper {
    TestAttemptScore toEntity(TestAttemptScoreDto dto);
    TestAttemptScoreDto toDto(TestAttemptScore entity);
}