package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.mapper;

import dev.tolkach.psychologicalTestsApplication.domain.model.TestAttempt;
import dev.tolkach.psychologicalTestsApplication.domain.model.TestAttemptFilter;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto.TestAttemptDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestAttemptDtoMapper {
    TestAttempt toEntity(TestAttemptDto dto);
    TestAttemptDto toDto(TestAttempt entity);
    TestAttemptDto toDto(TestAttemptFilter filter);
    TestAttemptFilter toFilter(TestAttemptDto dto);
}