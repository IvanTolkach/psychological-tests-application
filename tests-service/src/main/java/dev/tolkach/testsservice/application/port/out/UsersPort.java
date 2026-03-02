package dev.tolkach.testsservice.application.port.out;

import java.util.UUID;

public interface UsersPort {
    void validateAdminExists(UUID adminId);
}
