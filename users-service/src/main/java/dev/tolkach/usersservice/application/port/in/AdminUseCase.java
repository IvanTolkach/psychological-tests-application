package dev.tolkach.usersservice.application.port.in;

import dev.tolkach.usersservice.application.model.Admin;

import java.util.List;
import java.util.UUID;

public interface AdminUseCase {
    List<Admin> getAdminsByFilter(Admin filter);
    Admin getAdminById(UUID id);
    Admin createUpdateAdmin(Admin admin);
    void changePassword(UUID id, String oldPassword, String newPassword);
    void deactivateAdmin(UUID id);
}
