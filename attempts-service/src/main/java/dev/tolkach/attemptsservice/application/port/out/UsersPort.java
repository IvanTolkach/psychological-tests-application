package dev.tolkach.attemptsservice.application.port.out;

import common.dto.FacultyDto;
import common.dto.StudentDto;

import java.util.List;
import java.util.UUID;

public interface UsersPort {
    void validateStudentExists(UUID studentId);
    StudentDto getStudentById(UUID studentId);
    List<StudentDto> searchStudents(StudentDto filter);
    List<FacultyDto> getAllFaculties();
}
