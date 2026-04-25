package dev.tolkach.usersservice.adapter.in.rest;

import dev.tolkach.usersservice.adapter.in.rest.dto.StudentDto;
import dev.tolkach.usersservice.adapter.in.rest.endpoint.StudentEndpoint;
import dev.tolkach.usersservice.adapter.in.rest.mapper.StudentDtoMapper;
import dev.tolkach.usersservice.application.model.Student;
import dev.tolkach.usersservice.application.port.in.StudentUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class StudentController implements StudentEndpoint {

    private final StudentUseCase studentUseCase;
    private final StudentDtoMapper studentDtoMapper;

    public StudentController(StudentUseCase studentUseCase, StudentDtoMapper studentDtoMapper) {
        this.studentUseCase = studentUseCase;
        this.studentDtoMapper = studentDtoMapper;
    }

    @Override
    public ResponseEntity<List<StudentDto>> getStudents(StudentDto filter) {
        Student studentFilter = studentDtoMapper.toEntity(filter);
        List<Student> students = studentUseCase.getStudentsByFilter(studentFilter);
        List<StudentDto> responseDtos = students.stream().map(studentDtoMapper::toDto).toList();
        return ResponseEntity.ok(responseDtos);
    }

    @Override
    public ResponseEntity<StudentDto> getStudentById(UUID studentId) {
        Student student = studentUseCase.getStudentById(studentId);
        StudentDto responseDto = studentDtoMapper.toDto(student);
        return ResponseEntity.ok(responseDto);
    }

    @Override
    public ResponseEntity<StudentDto> createUpdateStudent(StudentDto dto) {
        Student student = studentDtoMapper.toEntity(dto);
        Student saved = studentUseCase.createUpdateStudent(student);
        StudentDto responseDto = studentDtoMapper.toDto(saved);
        if (dto.getId() == null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } else {
            return ResponseEntity.ok(responseDto);
        }
    }

    @Override
    public ResponseEntity<Void> deleteStudent(UUID studentId) {
        studentUseCase.deleteStudent(studentId);
        return ResponseEntity.noContent().build();
    }
}
