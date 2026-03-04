package dev.tolkach.usersservice.adapter.out.persistence.mapper;

import dev.tolkach.usersservice.adapter.out.persistence.entity.AdminEntity;
import dev.tolkach.usersservice.application.model.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdminMapper {
    AdminEntity toEntity(Admin admin);
    Admin toDomain(AdminEntity entity);
}
