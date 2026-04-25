package dev.tolkach.usersservice.application.port.in;

import dev.tolkach.usersservice.application.model.Admin;
import dev.tolkach.usersservice.application.model.AdminRole;
import dev.tolkach.usersservice.application.model.PasswordChange;

import java.util.List;
import java.util.UUID;

public interface AdminUseCase {
    List<Admin> getAdminsByFilter(Admin filter);
    Admin getAdminById(UUID id);
    Admin getAdminByEmail(String email);
    Admin createUpdateAdmin(Admin admin);
    void changePassword(PasswordChange passwordChange);
    void deactivateAdmin(UUID id);
    void activateAdmin(UUID id);
    void changeRole(UUID id, AdminRole newRole);
    Admin getCuttentAdmin();
}
