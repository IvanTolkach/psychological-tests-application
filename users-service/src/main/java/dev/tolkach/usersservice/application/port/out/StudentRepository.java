package dev.tolkach.usersservice.application.port.out;

import dev.tolkach.usersservice.application.model.Student;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository {
    Student save(Student student);
    Optional<Student> findById(UUID id);
    List<Student> findByFilter(Student filter);
    void deleteById(UUID id);
}
