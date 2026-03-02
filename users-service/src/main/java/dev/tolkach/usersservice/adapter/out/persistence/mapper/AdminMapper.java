package dev.tolkach.usersservice.adapter.out.persistence.mapper;

import dev.tolkach.usersservice.adapter.out.persistence.entity.AdminEntity;
import dev.tolkach.usersservice.application.model.Admin;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    AdminEntity toEntity(Admin admin);
    Admin toDomain(AdminEntity entity);
}
