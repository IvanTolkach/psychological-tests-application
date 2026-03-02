package dev.tolkach.usersservice.application.service;

import dev.tolkach.usersservice.application.model.Admin;
import dev.tolkach.usersservice.application.model.AdminRole;
import dev.tolkach.usersservice.application.port.in.AdminUseCase;
import dev.tolkach.usersservice.application.port.out.AdminRepository;
import dev.tolkach.usersservice.application.port.out.PasswordPort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

public class AdminService implements AdminUseCase {

    private final AdminRepository adminRepository;
    private final PasswordPort passwordPort;

    public AdminService(AdminRepository adminRepository, PasswordPort passwordPort) {
        this.adminRepository = adminRepository;
        this.passwordPort = passwordPort;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Admin> getAdminsByFilter(Admin filter) {
        return adminRepository.findByFilter(filter);
    }

    @Override
    @Transactional(readOnly = true)
    public Admin getAdminById(UUID id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Admin not found with id: " + id));
    }

    @Override
    @Transactional
    public Admin createUpdateAdmin(Admin admin) {
        Optional<Admin> existingByEmail = adminRepository.findByEmail(admin.getEmail());
        if (existingByEmail.isPresent()) {
            if (admin.getId() == null || !existingByEmail.get().getId().equals(admin.getId())) {
                throw new IllegalArgumentException("Email already exists: " + admin.getEmail());
            }
        }

        if (admin.getId() == null) {
            if (admin.getEmail() == null || admin.getEmail().isBlank()) {
                throw new IllegalArgumentException("Email is required for creation");
            }
            if (admin.getPassword() == null || admin.getPassword().isBlank()) {
                throw new IllegalArgumentException("Password is required for creation");
            }
            if (!passwordPort.isStrong(admin.getPassword(), admin.getSname(), admin.getEmail(), admin.getPhoneNumber())) {
                throw new IllegalArgumentException("Password is too weak. It must be at least 12 characters long, " +
                        "contain uppercase, lowercase, digit and special character, and avoid common patterns.");
            }
            admin.setPassword(passwordPort.encode(admin.getPassword()));
            admin.setRole(AdminRole.STANDARD);
            admin.setIsActive(false);
            return adminRepository.save(admin);
        } else {
            Admin existing = adminRepository.findById(admin.getId())
                    .orElseThrow(() -> new NoSuchElementException("Admin not found with id: " + admin.getId()));
            existing.setSname(admin.getSname());
            existing.setFname(admin.getFname());
            existing.setMname(admin.getMname());
            existing.setPhoneNumber(admin.getPhoneNumber());
            return adminRepository.save(existing);
        }
    }

    @Override
    @Transactional
    public void changePassword(UUID id, String oldPassword, String newPassword) {
        Admin existing = adminRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Admin not found with id: " + id));

        if (!passwordPort.matches(oldPassword, existing.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        if (!passwordPort.isStrong(newPassword, existing.getSname(), existing.getEmail(), existing.getPhoneNumber())) {
            throw new IllegalArgumentException("New password is too weak. It must be at least 12 characters long, " +
                    "contain uppercase, lowercase, digit and special character, and avoid common patterns.");
        }

        if (passwordPort.matches(newPassword, existing.getPassword())) {
            throw new IllegalArgumentException("New password cannot be the same as an old password");
        }

        existing.setPassword(passwordPort.encode(newPassword));
        adminRepository.save(existing);
    }

    //TODO может только SUPER_ADMIN
    @Override
    @Transactional
    public void deactivateAdmin(UUID id) {
        Admin existing = adminRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Admin not found with id: " + id));
        if (!existing.getIsActive()) {
            throw new IllegalArgumentException("Admin is already deactivated with id: " + id);
        }
        existing.setIsActive(false);
        adminRepository.save(existing);
    }

    //TODO отдельная логика для смены ROLE и IS_ACTIVE
}
