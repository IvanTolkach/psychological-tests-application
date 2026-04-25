package dev.tolkach.usersservice.adapter.in.rest.mapper;

import dev.tolkach.usersservice.adapter.in.rest.dto.JwtResponseDto;
import dev.tolkach.usersservice.application.model.JwtResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JwtResponseDtoMapper {
    JwtResponse toEntity(JwtResponseDto dto);
    JwtResponseDto toDto(JwtResponse entity);
}

