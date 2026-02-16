package dev.tolkach.psychologicalTestsApplication.domain.port.out;

import dev.tolkach.psychologicalTestsApplication.domain.model.Student;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository {
    Student save(Student student);
    Optional<Student> findById(UUID id);
    List<Student> findByFilter(Student filter);
    void deleteById(UUID id);
}
