package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.mapper;

import dev.tolkach.psychologicalTestsApplication.domain.model.Student;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto.StudentDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentDtoMapper {
    Student toEntity(StudentDto dto);
    StudentDto toDto(Student entity);
}
