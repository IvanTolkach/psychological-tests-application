package dev.tolkach.attemptsservice.application.port.in;

import dev.tolkach.attemptsservice.application.model.StudentAnswer;

import java.util.List;
import java.util.UUID;

public interface StudentAnswerUseCase {
    List<StudentAnswer> getStudentAnswersByFilter(StudentAnswer filter);
    StudentAnswer createUpdateStudentAnswer(StudentAnswer studentAnswer);
    void deleteStudentAnswer(UUID id);
}
