package dev.tolkach.usersservice.adapter.in;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import dev.tolkach.usersservice.adapter.in.rest.StudentController;
import dev.tolkach.usersservice.adapter.in.rest.dto.StudentDto;
import dev.tolkach.usersservice.adapter.in.rest.mapper.StudentDtoMapper;
import dev.tolkach.usersservice.application.model.Student;
import dev.tolkach.usersservice.application.port.in.StudentUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;


@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    StudentUseCase studentUseCase;

    @Mock
    StudentDtoMapper studentDtoMapper;

    @InjectMocks
    StudentController controller;

    private Student student;
    private StudentDto studentDto;
    private UUID studentId;

    @BeforeEach
    void setup() {
        studentId = UUID.randomUUID();

        student = new Student();
        student.setId(studentId);
        student.setFname("John");

        studentDto = new StudentDto();
        studentDto.setId(studentId);
        studentDto.setFname("John");
    }

    @Test
    void getStudents_returnsDtoList() {
        when(studentDtoMapper.toEntity(studentDto)).thenReturn(student);
        when(studentUseCase.getStudentsByFilter(student)).thenReturn(List.of(student));
        when(studentDtoMapper.toDto(student)).thenReturn(studentDto);

        ResponseEntity<List<StudentDto>> response = controller.getStudents(studentDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(studentDto, response.getBody().getFirst());
    }

    @Test
    void getStudentById_returnsDto() {
        when(studentUseCase.getStudentById(studentId)).thenReturn(student);
        when(studentDtoMapper.toDto(student)).thenReturn(studentDto);

        ResponseEntity<StudentDto> response = controller.getStudentById(studentId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(studentDto, response.getBody());
    }

    @Test
    void createUpdateStudent_creates_returnsCreated() {
        StudentDto dto = new StudentDto();
        dto.setId(null);

        when(studentDtoMapper.toEntity(dto)).thenReturn(student);
        when(studentUseCase.createUpdateStudent(student)).thenReturn(student);
        when(studentDtoMapper.toDto(student)).thenReturn(studentDto);

        ResponseEntity<StudentDto> response = controller.createUpdateStudent(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(studentDto, response.getBody());
    }

    @Test
    void createUpdateStudent_updates_returnsOk() {
        StudentDto dto = new StudentDto();
        dto.setId(studentId);

        when(studentDtoMapper.toEntity(dto)).thenReturn(student);
        when(studentUseCase.createUpdateStudent(student)).thenReturn(student);
        when(studentDtoMapper.toDto(student)).thenReturn(studentDto);

        ResponseEntity<StudentDto> response = controller.createUpdateStudent(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(studentDto, response.getBody());
    }

    @Test
    void deleteStudent_returnsNoContent() {
        ResponseEntity<Void> response = controller.deleteStudent(studentId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(studentUseCase).deleteStudent(studentId);
    }
}
