package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.mapper;

import dev.tolkach.psychologicalTestsApplication.domain.model.Admin;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.out.persistence.entity.AdminEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    AdminEntity toEntity(Admin student);
    Admin toDomain(AdminEntity entity);
}
