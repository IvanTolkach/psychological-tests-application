package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.mapper;

import dev.tolkach.psychologicalTestsApplication.domain.model.Faculty;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.entity.FacultyEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FacultyMapper {
    FacultyEntity toEntity(Faculty faculty);
    Faculty toDomain(FacultyEntity entity);
}
