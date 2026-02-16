package dev.tolkach.psychologicalTestsApplication.domain.port.in;

import dev.tolkach.psychologicalTestsApplication.domain.model.Student;

import java.util.List;
import java.util.UUID;

public interface StudentUseCase {
    List<Student> getStudentsByFilter(Student filter);
    Student createUpdateStudent(Student student);
    void deleteStudent(UUID id);
}
