package dev.tolkach.usersservice.application.port.in;

import dev.tolkach.usersservice.application.model.Student;

import java.util.List;
import java.util.UUID;

public interface StudentUseCase {
    List<Student> getStudentsByFilter(Student filter);
    Student getStudentById(UUID id);
    Student createUpdateStudent(Student student);
    void deleteStudent(UUID id);
}
