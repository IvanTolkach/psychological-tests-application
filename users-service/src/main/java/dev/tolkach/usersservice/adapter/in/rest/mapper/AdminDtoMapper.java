package dev.tolkach.usersservice.adapter.in.rest.mapper;

import dev.tolkach.usersservice.adapter.in.rest.dto.AdminDto;
import dev.tolkach.usersservice.application.model.Admin;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminDtoMapper {
    Admin toEntity(AdminDto dto);
    AdminDto toDto(Admin entity);
}
