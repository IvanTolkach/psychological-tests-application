package dev.tolkach.attemptsservice.application.port.out;

import java.util.UUID;

public interface UsersPort {
    void validateStudentExists(UUID studentId);
}
