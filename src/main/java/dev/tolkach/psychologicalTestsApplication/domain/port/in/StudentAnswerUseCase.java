package dev.tolkach.psychologicalTestsApplication.domain.port.in;

import dev.tolkach.psychologicalTestsApplication.domain.model.StudentAnswer;

import java.util.List;
import java.util.UUID;

public interface StudentAnswerUseCase {
    List<StudentAnswer> getStudentAnswersByFilter(StudentAnswer filter);
    StudentAnswer createUpdateStudentAnswer(StudentAnswer studentAnswer);
    void deleteStudentAnswer(UUID id);
}
