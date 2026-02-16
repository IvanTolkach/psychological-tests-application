package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest;

import dev.tolkach.psychologicalTestsApplication.domain.model.Student;
import dev.tolkach.psychologicalTestsApplication.domain.port.in.StudentUseCase;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto.StudentDto;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.endpoint.StudentEndpoint;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.mapper.StudentDtoMapper;
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
