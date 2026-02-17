package dev.tolkach.psychologicalTestsApplication.domain.port.out;

import dev.tolkach.psychologicalTestsApplication.domain.model.Admin;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AdminRepository {
    Admin save(Admin admin);
    Optional<Admin> findById(UUID id);
    Optional<Admin> findByEmail(String email);
    List<Admin> findByFilter(Admin filter);
}
