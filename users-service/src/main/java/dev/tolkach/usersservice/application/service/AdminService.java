package dev.tolkach.usersservice.application.service;

import dev.tolkach.usersservice.application.model.Admin;
import dev.tolkach.usersservice.application.model.AdminRole;
import dev.tolkach.usersservice.application.model.PasswordChange;
import dev.tolkach.usersservice.application.port.in.AdminUseCase;
import dev.tolkach.usersservice.application.port.out.AdminRepository;
import dev.tolkach.usersservice.application.port.out.PasswordPort;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public class AdminService implements AdminUseCase {

    @Value("${admin.limits.max-super-admins}")
    @Getter
    @Setter
    private int maxSuperAdmins;

    private final AdminRepository adminRepository;
    private final PasswordPort passwordPort;
    private final TokenRevocationService tokenRevocationService;

    public AdminService(AdminRepository adminRepository, PasswordPort passwordPort, TokenRevocationService tokenRevocationService) {
        this.adminRepository = adminRepository;
        this.passwordPort = passwordPort;
        this.tokenRevocationService = tokenRevocationService;
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
    @Transactional(readOnly = true)
    public Admin getAdminByEmail(String email) {
        return adminRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Admin not found with email: " + email));
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

            Admin currentAdmin = getCuttentAdmin();
            if (!currentAdmin.getRole().equals(AdminRole.SUPER) && !currentAdmin.equals(existing)) {
                throw new AccessDeniedException("The administrator can only change his own data if he does not have the SUPER role");
            }

            if (existing.getRole().equals(AdminRole.SUPER)) {
                throw new AccessDeniedException("You cannot change data of the SUPER administrator");
            }

            existing.setSname(admin.getSname());
            existing.setFname(admin.getFname());
            existing.setMname(admin.getMname());
            existing.setPhoneNumber(admin.getPhoneNumber());
            return adminRepository.save(existing);
        }
    }

    @Override
    @Transactional
    public void changePassword(PasswordChange passwordChange) {
        Admin existing = adminRepository.findById(passwordChange.getAdminId())
                .orElseThrow(() -> new NoSuchElementException("Admin not found with id: " + passwordChange.getAdminId()));

        Admin currentAdmin = getCuttentAdmin();
        if (!currentAdmin.getRole().equals(AdminRole.SUPER) && !currentAdmin.equals(existing)) {
            throw new AccessDeniedException("The administrator can only change his own password if he does not have the SUPER role");
        }

        if (existing.getRole().equals(AdminRole.SUPER)) {
            throw new AccessDeniedException("You cannot change password of the SUPER administrator");
        }

        if (!passwordPort.matches(passwordChange.getOldPassword(), existing.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        if (!passwordPort.isStrong(passwordChange.getNewPassword(), existing.getSname(), existing.getEmail(), existing.getPhoneNumber())) {
            throw new IllegalArgumentException("New password is too weak. It must be at least 12 characters long, " +
                    "contain uppercase, lowercase, digit and special character, and avoid common patterns.");
        }

        if (passwordPort.matches(passwordChange.getNewPassword(), existing.getPassword())) {
            throw new IllegalArgumentException("New password cannot be the same as an old password");
        }

        existing.setPassword(passwordPort.encode(passwordChange.getNewPassword()));
        adminRepository.save(existing);

        tokenRevocationService.revokeUserTokens(existing.getId());
    }

    @Override
    @Transactional
    public void deactivateAdmin(UUID id) {
        Admin existing = adminRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Admin not found with id: " + id));

        if (existing.getRole().equals(AdminRole.SUPER)) {
            throw new AccessDeniedException("Admin with SUPER role cannot be deactivated. Admin id: " + id);
        }

        if (!existing.getIsActive()) {
            throw new IllegalArgumentException("Admin is already deactivated with id: " + id);
        }

        if (getCuttentAdmin().equals(existing)) {
            throw new IllegalArgumentException("Current admin cannot deactivate himself");
        }

        existing.setIsActive(false);
        adminRepository.save(existing);

        tokenRevocationService.revokeUserTokens(existing.getId());
    }

    @Override
    @Transactional
    public void activateAdmin(UUID id) {
        Admin existing = adminRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Admin not found with id: " + id));
        if (existing.getIsActive()) {
            throw new IllegalArgumentException("Admin is already activated with id: " + id);
        }

        existing.setIsActive(true);
        adminRepository.save(existing);

        tokenRevocationService.revokeUserTokens(existing.getId());
    }

    @Override
    @Transactional
    public void changeRole(UUID id, AdminRole newRole) {
        Admin existing = adminRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Admin not found with id: " + id));

        if (!existing.getIsActive()) {
            throw new IllegalArgumentException("The role of admin without active status cannot be changed. Admin id: " + id);
        }

        if (getCuttentAdmin().equals(existing)) {
            throw new IllegalArgumentException("Current admin cannot change role for himself");
        }

        if (existing.getRole().equals(newRole)) {
            throw new IllegalArgumentException("Admin with id [" + id + "] already has role [" + newRole + "]");
        }

        if (newRole.equals(AdminRole.SUPER)) {
            Admin adminFilter = new Admin();
            adminFilter.setRole(AdminRole.SUPER);
            if (getAdminsByFilter(adminFilter).size() >= maxSuperAdmins) {
                throw new IllegalArgumentException("Admin cannot have SUPER role because maximum number of SUPER admins has been exceeded [" + maxSuperAdmins + "]");
            }
        }

        existing.setRole(newRole);
        adminRepository.save(existing);

        tokenRevocationService.revokeUserTokens(existing.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Admin getCuttentAdmin() {
        String email = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        return getAdminByEmail(email);
    }
}
