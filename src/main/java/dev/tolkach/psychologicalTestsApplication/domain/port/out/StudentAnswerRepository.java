package dev.tolkach.psychologicalTestsApplication.domain.port.out;

import dev.tolkach.psychologicalTestsApplication.domain.model.StudentAnswer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentAnswerRepository {
    StudentAnswer save(StudentAnswer studentAnswer);
    Optional<StudentAnswer> findById(UUID id);
    List<StudentAnswer> findByFilter(StudentAnswer filter);
    void deleteById(UUID id);
}
