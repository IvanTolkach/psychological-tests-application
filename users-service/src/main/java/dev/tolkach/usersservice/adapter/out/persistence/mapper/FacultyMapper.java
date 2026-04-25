package dev.tolkach.usersservice.adapter.out.persistence.mapper;

import dev.tolkach.usersservice.adapter.out.persistence.entity.FacultyEntity;
import dev.tolkach.usersservice.application.model.Faculty;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FacultyMapper {
    FacultyEntity toEntity(Faculty faculty);
    Faculty toDomain(FacultyEntity entity);
}
