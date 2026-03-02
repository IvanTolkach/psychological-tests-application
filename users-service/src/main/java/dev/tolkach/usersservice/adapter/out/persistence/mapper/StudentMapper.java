package dev.tolkach.usersservice.adapter.out.persistence.mapper;

import dev.tolkach.usersservice.adapter.out.persistence.entity.StudentEntity;
import dev.tolkach.usersservice.application.model.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentEntity toEntity(Student student);
    Student toDomain(StudentEntity entity);
}