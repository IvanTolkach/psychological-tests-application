package dev.tolkach.usersservice.adapter.in.rest.mapper;

import dev.tolkach.usersservice.adapter.in.rest.dto.StudentDto;
import dev.tolkach.usersservice.application.model.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentDtoMapper {
    Student toEntity(StudentDto dto);
    StudentDto toDto(Student entity);
}
