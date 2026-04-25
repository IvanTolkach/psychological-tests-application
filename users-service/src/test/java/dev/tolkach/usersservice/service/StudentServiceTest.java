package dev.tolkach.usersservice.service;

import dev.tolkach.usersservice.application.model.Faculty;
import dev.tolkach.usersservice.application.model.Student;
import dev.tolkach.usersservice.application.port.out.FacultyRepository;
import dev.tolkach.usersservice.application.port.out.StudentRepository;
import dev.tolkach.usersservice.application.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    StudentRepository studentRepository;

    @Mock
    FacultyRepository facultyRepository;

    @InjectMocks
    StudentService service;

    Student student;
    UUID facultyId;

    @BeforeEach
    void setup() {

        facultyId = UUID.randomUUID();

        student = new Student();
        student.setId(UUID.randomUUID());
        student.setFacultyId(facultyId);
    }

    @Test
    void getStudentsByFilter_noFacultyCheck() {

        Student filter = new Student();

        when(studentRepository.findByFilter(filter))
                .thenReturn(List.of(student));

        List<Student> result = service.getStudentsByFilter(filter);

        assertEquals(1, result.size());
    }

    @Test
    void getStudentsByFilter_facultyNotFound() {

        Student filter = new Student();
        filter.setFacultyId(facultyId);

        when(facultyRepository.findById(facultyId))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> service.getStudentsByFilter(filter));
    }

    @Test
    void getStudentsByFilter_success() {

        Student filter = new Student();
        filter.setFacultyId(facultyId);

        when(facultyRepository.findById(facultyId))
                .thenReturn(Optional.of(new Faculty()));

        when(studentRepository.findByFilter(filter))
                .thenReturn(List.of(student));

        List<Student> result = service.getStudentsByFilter(filter);

        assertEquals(1, result.size());
    }

    @Test
    void getStudentById_notFound() {

        when(studentRepository.findById(any()))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> service.getStudentById(UUID.randomUUID()));
    }

    @Test
    void getStudentById_success() {

        when(studentRepository.findById(student.getId()))
                .thenReturn(Optional.of(student));

        Student result = service.getStudentById(student.getId());

        assertEquals(student, result);
    }

    @Test
    void createStudent_facultyNotFound() {

        student.setId(null);

        when(facultyRepository.findById(facultyId))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> service.createUpdateStudent(student));
    }

    @Test
    void createStudent_existingStudentReturned() {

        student.setId(null);

        when(facultyRepository.findById(facultyId))
                .thenReturn(Optional.of(new Faculty()));

        when(studentRepository.findByFilter(student))
                .thenReturn(List.of(student));

        Student result = service.createUpdateStudent(student);

        assertEquals(student, result);
    }

    @Test
    void createStudent_newSaved() {

        student.setId(null);

        when(facultyRepository.findById(facultyId))
                .thenReturn(Optional.of(new Faculty()));

        when(studentRepository.findByFilter(student))
                .thenReturn(List.of());

        when(studentRepository.save(student))
                .thenReturn(student);

        Student result = service.createUpdateStudent(student);

        assertEquals(student, result);
    }

    @Test
    void updateStudent_notFound() {

        when(facultyRepository.findById(facultyId))
                .thenReturn(Optional.of(new Faculty()));

        when(studentRepository.findById(student.getId()))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> service.createUpdateStudent(student));
    }

    @Test
    void updateStudent_success() {

        Student existing = new Student();
        existing.setId(student.getId());

        when(facultyRepository.findById(facultyId))
                .thenReturn(Optional.of(new Faculty()));

        when(studentRepository.findById(student.getId()))
                .thenReturn(Optional.of(existing));

        when(studentRepository.save(existing))
                .thenReturn(existing);

        Student result = service.createUpdateStudent(student);

        assertEquals(existing, result);
    }

    @Test
    void deleteStudent_notFound() {

        when(studentRepository.findById(any()))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> service.deleteStudent(UUID.randomUUID()));
    }

    @Test
    void deleteStudent_success() {

        when(studentRepository.findById(student.getId()))
                .thenReturn(Optional.of(student));

        service.deleteStudent(student.getId());

        verify(studentRepository).deleteById(student.getId());
    }
}