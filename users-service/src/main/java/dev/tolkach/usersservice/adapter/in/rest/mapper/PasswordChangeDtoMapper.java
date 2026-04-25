package dev.tolkach.usersservice.adapter.in.rest.mapper;

import dev.tolkach.usersservice.adapter.in.rest.dto.PasswordChangeDto;
import dev.tolkach.usersservice.application.model.PasswordChange;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PasswordChangeDtoMapper {
    PasswordChange toEntity(PasswordChangeDto dto);
    PasswordChangeDto toDto(PasswordChange entity);
}