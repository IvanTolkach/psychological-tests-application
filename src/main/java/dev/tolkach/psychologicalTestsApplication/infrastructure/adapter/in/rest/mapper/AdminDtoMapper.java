package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.mapper;

import dev.tolkach.psychologicalTestsApplication.domain.model.Admin;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto.AdminDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminDtoMapper {
    Admin toEntity(AdminDto faculty);
    AdminDto toDto(Admin entity);
}
