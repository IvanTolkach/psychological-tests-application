package dev.tolkach.usersservice.adapter.in.rest.mapper;

import dev.tolkach.usersservice.adapter.in.rest.dto.FacultyDto;
import dev.tolkach.usersservice.application.model.Faculty;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FacultyDtoMapper {
    Faculty toEntity(FacultyDto dto);
    FacultyDto toDto(Faculty entity);
}
