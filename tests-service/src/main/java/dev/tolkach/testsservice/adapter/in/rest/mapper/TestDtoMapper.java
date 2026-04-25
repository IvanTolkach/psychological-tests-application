package dev.tolkach.testsservice.adapter.in.rest.mapper;

import dev.tolkach.testsservice.adapter.in.rest.dto.TestDto;
import dev.tolkach.testsservice.application.model.Test;
import dev.tolkach.testsservice.application.model.TestFilter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TestDtoMapper {
    Test toEntity(TestDto dto);
    @Mapping(target = "createdAtFrom", ignore = true)
    @Mapping(target = "createdAtTo", ignore = true)
    @Mapping(target = "updatedAtFrom", ignore = true)
    @Mapping(target = "updatedAtTo", ignore = true)
    TestDto toDto(Test entity);
    TestFilter toFilter(TestDto dto);
}
