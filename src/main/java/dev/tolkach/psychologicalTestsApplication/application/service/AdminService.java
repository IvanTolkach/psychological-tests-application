package dev.tolkach.psychologicalTestsApplication.application.service;

import dev.tolkach.psychologicalTestsApplication.domain.model.Admin;
import dev.tolkach.psychologicalTestsApplication.domain.model.AdminRole;
import dev.tolkach.psychologicalTestsApplication.domain.port.in.AdminUseCase;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AdminService implements AdminUseCase {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Admin> getAdminsByFilter(Admin filter) {
        return adminRepository.findByFilter(filter);
    }

    @Override
    public Admin createUpdateAdmin(Admin admin) {
        Optional<Admin> existingByEmail = adminRepository.findByEmail(admin.getEmail());
        if (existingByEmail.isPresent()) {
            if (admin.getId() == null || !existingByEmail.get().getId().equals(admin.getId())) {
                throw new IllegalArgumentException("Email already exists: " + admin.getEmail());
            }
        }

        if (admin.getId() == null) {
            admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            admin.setRole(AdminRole.STANDARD);
            admin.setIsActive(false);
            return adminRepository.save(admin);
        } else {
            Admin existing = adminRepository.findById(admin.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Admin not found with id: " + admin.getId()));
            existing.setSname(admin.getSname());
            existing.setFname(admin.getFname());
            existing.setMname(admin.getMname());
            existing.setPhoneNumber(admin.getPhoneNumber());
            return adminRepository.save(existing);
        }
    }

    @Override
    public void changePassword(UUID id, String oldPassword, String newPassword) {
        Admin existing = adminRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found with id: " + id));

        if (!passwordEncoder.matches(oldPassword, existing.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        if (newPassword == null || newPassword.length() < 8) {
            throw new IllegalArgumentException("New password must be at least 8 characters");
        }

        existing.setPassword(passwordEncoder.encode(newPassword));
        adminRepository.save(existing);
    }

    //TODO может только SUPER_ADMIN
    @Override
    public Admin deactivateAdmin(UUID id) {
        Admin existing = adminRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found with id: " + id));
        if (!existing.getIsActive()) {
            throw new IllegalArgumentException("Admin is already deactivated with id: " + id);
        }
        existing.setIsActive(false);
        return adminRepository.save(existing);
    }

    //TODO отдельная логика для смены ROLE и IS_ACTIVE
}
