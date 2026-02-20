package dev.tolkach.psychologicalTestsApplication.domain.port.in;

import dev.tolkach.psychologicalTestsApplication.domain.model.Admin;

import java.util.List;
import java.util.UUID;

public interface AdminUseCase {
    List<Admin> getAdminsByFilter(Admin filter);
    Admin createUpdateAdmin(Admin admin);
    void changePassword(UUID id, String oldPassword, String newPassword);
    void deactivateAdmin(UUID id);
}
