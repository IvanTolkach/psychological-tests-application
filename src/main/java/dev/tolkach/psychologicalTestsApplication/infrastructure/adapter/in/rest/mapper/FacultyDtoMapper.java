package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.mapper;

import dev.tolkach.psychologicalTestsApplication.domain.model.Faculty;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto.FacultyDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FacultyDtoMapper {
    Faculty toEntity(FacultyDto faculty);
    FacultyDto toDto(Faculty entity);
}
