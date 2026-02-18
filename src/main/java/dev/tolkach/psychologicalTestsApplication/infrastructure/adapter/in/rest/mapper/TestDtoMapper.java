package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.mapper;

import dev.tolkach.psychologicalTestsApplication.domain.model.Test;
import dev.tolkach.psychologicalTestsApplication.domain.model.TestFilter;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto.TestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestDtoMapper {
    Test toEntity(TestDto dto);
    TestDto toDto(Test entity);
    TestDto toDto(TestFilter filter);
    TestFilter toFilter(TestDto dto);
}
