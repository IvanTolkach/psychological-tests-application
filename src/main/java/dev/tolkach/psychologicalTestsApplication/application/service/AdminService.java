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
            if (admin.getEmail() == null || admin.getEmail().isBlank()) {
                throw new IllegalArgumentException("Email is required for creation");
            }
            if (admin.getPassword() == null || admin.getPassword().isBlank()) {
                throw new IllegalArgumentException("Password is required for creation");
            }
            if (!isPasswordStrong(admin.getPassword(), admin.getSname(), admin.getEmail(), admin.getPhoneNumber())) {
                throw new IllegalArgumentException("Password is too weak. It must be at least 12 characters long, " +
                        "contain uppercase, lowercase, digit and special character, and avoid common patterns.");
            }
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

        if (!isPasswordStrong(newPassword, existing.getSname(), existing.getEmail(), existing.getPhoneNumber())) {
            throw new IllegalArgumentException("New password is too weak. It must be at least 12 characters long, " +
                    "contain uppercase, lowercase, digit and special character, and avoid common patterns.");
        }

        if (passwordEncoder.matches(newPassword, existing.getPassword())) {
            throw new IllegalArgumentException("New password cannot be the same as an old password");
        }

        existing.setPassword(passwordEncoder.encode(newPassword));
        adminRepository.save(existing);
    }

    //TODO может только SUPER_ADMIN
    @Override
    public void deactivateAdmin(UUID id) {
        Admin existing = adminRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found with id: " + id));
        if (!existing.getIsActive()) {
            throw new IllegalArgumentException("Admin is already deactivated with id: " + id);
        }
        existing.setIsActive(false);
        adminRepository.save(existing);
    }

    //TODO отдельная логика для смены ROLE и IS_ACTIVE

    public static boolean isPasswordStrong(String password, String sname, String email, String phoneNumber) {
        if (password == null || password.length() < 12) {
            return false;
        }

        boolean hasUpper    = false;
        boolean hasLower    = false;
        boolean hasDigit    = false;
        boolean hasSpecial  = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c))   hasUpper   = true;
            else if (Character.isLowerCase(c)) hasLower   = true;
            else if (Character.isDigit(c))     hasDigit   = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }

        if (!hasUpper || !hasLower || !hasDigit || !hasSpecial) {
            return false;
        }

        int repeatCount = 1;
        char prev = password.charAt(0);
        for (int i = 1; i < password.length(); i++) {
            char current = password.charAt(i);
            if (current == prev) {
                repeatCount++;
                if (repeatCount >= 4) {
                    return false;
                }
            } else {
                repeatCount = 1;
            }
            prev = current;
        }

        String keyboardSeqs = "qwertyuiopasdfghjklzxcvbnm1234567890!@#$%^&*()";
        String reversed = new StringBuilder(keyboardSeqs).reverse().toString();

        String lowerPass = password.toLowerCase();
        for (int i = 0; i < keyboardSeqs.length() - 3; i++) {
            String seq = keyboardSeqs.substring(i, i + 4);
            String revSeq = reversed.substring(reversed.length() - (i + 4), reversed.length() - i);

            if (lowerPass.contains(seq) || lowerPass.contains(revSeq)) {
                return false;
            }
        }

        String lowerSname = (sname != null ? sname.toLowerCase() : "");
        String lowerEmail = (email != null ? email.toLowerCase() : "");

        String[] weakPatterns = {
                "password", "123456", "qwerty", "admin", "пароль", "йцукен",
                "12345678", "parol", "123123", "privet", "abc123", "111111",
                "qwerty123", "password1", "admin123", "йцукен123", "bntu", "бнту",
                lowerSname, lowerEmail.split("@")[0], phoneNumber.substring(1)
        };

        for (String weak : weakPatterns) {
            if (weak != null && !weak.isEmpty() && lowerPass.contains(weak)) {
                return false;
            }
        }

        return true;
    }
}
