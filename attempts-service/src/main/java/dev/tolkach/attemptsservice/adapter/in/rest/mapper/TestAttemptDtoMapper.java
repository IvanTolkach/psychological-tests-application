package dev.tolkach.attemptsservice.adapter.in.rest.mapper;

import dev.tolkach.attemptsservice.adapter.in.rest.dto.TestAttemptDto;
import dev.tolkach.attemptsservice.application.model.TestAttempt;
import dev.tolkach.attemptsservice.application.model.TestAttemptFilter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TestAttemptDtoMapper {
    TestAttempt toEntity(TestAttemptDto dto);
    @Mapping(target = "attemptDateFrom", ignore = true)
    @Mapping(target = "attemptDateTo", ignore = true)
    TestAttemptDto toDto(TestAttempt entity);
    TestAttemptFilter toFilter(TestAttemptDto dto);
}