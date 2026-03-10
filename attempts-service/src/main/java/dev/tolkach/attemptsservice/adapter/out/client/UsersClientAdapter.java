package dev.tolkach.attemptsservice.adapter.out.client;

import common.dto.FacultyDto;
import common.dto.StudentDto;
import dev.tolkach.attemptsservice.application.port.out.UsersPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Component
public class UsersClientAdapter implements UsersPort {

    private final UsersClient usersClient;

    public UsersClientAdapter(UsersClient usersClient) {
        this.usersClient = usersClient;
    }

    @Override
    public void validateStudentExists(UUID studentId) {
        Object response = usersClient.getStudent(studentId);
        if (response == null) {
            throw new NoSuchElementException("Student not found with id: " + studentId);
        }
    }

    @Override
    public List<StudentDto> searchStudents(StudentDto filter) {
        return usersClient.searchStudents(filter);
    }

    @Override
    public List<FacultyDto> getAllFaculties() {
        return usersClient.searchFaculties(new Object());
    }
}
