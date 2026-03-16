package dev.tolkach.usersservice.service;

import dev.tolkach.usersservice.application.model.Admin;
import dev.tolkach.usersservice.application.model.AdminRole;
import dev.tolkach.usersservice.application.model.PasswordChange;
import dev.tolkach.usersservice.application.port.out.AdminRepository;
import dev.tolkach.usersservice.application.port.out.PasswordPort;
import dev.tolkach.usersservice.application.service.AdminService;
import dev.tolkach.usersservice.application.service.TokenRevocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {
    @Mock
    AdminRepository adminRepository;

    @Mock
    PasswordPort passwordPort;

    @Mock
    TokenRevocationService tokenRevocationService;

    @InjectMocks
    AdminService service;

    Admin admin;

    @BeforeEach
    void setup() {
        admin = new Admin();
        admin.setId(UUID.randomUUID());
        admin.setEmail("test@mail.com");
        admin.setPassword("encoded");
        admin.setRole(AdminRole.STANDARD);
        admin.setIsActive(true);
    }

    @Test
    void getAdminById_success() {
        when(adminRepository.findById(admin.getId()))
                .thenReturn(Optional.of(admin));

        Admin result = service.getAdminById(admin.getId());

        assertEquals(admin, result);
    }

    @Test
    void getAdminById_notFound() {
        when(adminRepository.findById(any()))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> service.getAdminById(UUID.randomUUID()));
    }

    @Test
    void createAdmin_success() {

        Admin newAdmin = new Admin();
        newAdmin.setEmail("new@mail.com");
        newAdmin.setPassword("StrongPass123!");

        when(adminRepository.findByEmail(any()))
                .thenReturn(Optional.empty());

        when(passwordPort.isStrong(any(), any(), any(), any()))
                .thenReturn(true);

        when(passwordPort.encode(any()))
                .thenReturn("encoded");

        when(adminRepository.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        Admin result = service.createUpdateAdmin(newAdmin);

        assertEquals(AdminRole.STANDARD, result.getRole());
        assertFalse(result.getIsActive());

        verify(adminRepository).save(any());
    }

    @Test
    void createAdmin_emailExists() {

        Admin existing = new Admin();
        existing.setId(UUID.randomUUID());

        Admin newAdmin = new Admin();
        newAdmin.setEmail("mail@mail.com");

        when(adminRepository.findByEmail(any()))
                .thenReturn(Optional.of(existing));

        assertThrows(IllegalArgumentException.class,
                () -> service.createUpdateAdmin(newAdmin));
    }

    @Test
    void createAdmin_weakPassword() {

        Admin newAdmin = new Admin();
        newAdmin.setEmail("mail@mail.com");
        newAdmin.setPassword("weak");

        when(adminRepository.findByEmail(any()))
                .thenReturn(Optional.empty());

        when(passwordPort.isStrong(any(), any(), any(), any()))
                .thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                () -> service.createUpdateAdmin(newAdmin));
    }

    @Test
    void createAdmin_emailNull() {

        Admin newAdmin = new Admin();
        newAdmin.setEmail(null);
        newAdmin.setPassword("StrongPass123!");

        when(adminRepository.findByEmail(null))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> service.createUpdateAdmin(newAdmin));
    }

    @Test
    void createAdmin_emailBlank() {

        Admin newAdmin = new Admin();
        newAdmin.setEmail(" ");
        newAdmin.setPassword("StrongPass123!");

        when(adminRepository.findByEmail(" "))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> service.createUpdateAdmin(newAdmin));
    }

    @Test
    void createAdmin_passwordNull() {

        Admin newAdmin = new Admin();
        newAdmin.setEmail("mail@test.com");
        newAdmin.setPassword(null);

        when(adminRepository.findByEmail(any()))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> service.createUpdateAdmin(newAdmin));
    }

    @Test
    void createAdmin_passwordBlank() {

        Admin newAdmin = new Admin();
        newAdmin.setEmail("mail@test.com");
        newAdmin.setPassword(" ");

        when(adminRepository.findByEmail(any()))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> service.createUpdateAdmin(newAdmin));
    }

    @Test
    void updateAdmin_notFound() {

        Admin update = new Admin();
        update.setId(UUID.randomUUID());
        update.setEmail("mail@test.com");

        when(adminRepository.findByEmail("mail@test.com"))
                .thenReturn(Optional.empty());

        when(adminRepository.findById(update.getId()))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> service.createUpdateAdmin(update));
    }

    @Test
    void updateAdmin_superAdminForbidden() {

        UUID id = UUID.randomUUID();

        Admin existing = new Admin();
        existing.setId(id);
        existing.setEmail("super@mail.com");
        existing.setRole(AdminRole.SUPER);

        Admin update = new Admin();
        update.setId(id);
        update.setEmail("super@mail.com");

        when(adminRepository.findByEmail("super@mail.com"))
                .thenReturn(Optional.of(existing));

        when(adminRepository.findById(id))
                .thenReturn(Optional.of(existing));

        mockSecurity(createAnotherAdmin());

        assertThrows(AccessDeniedException.class,
                () -> service.createUpdateAdmin(update));
    }

    @Test
    void updateAdmin_success() {

        UUID id = UUID.randomUUID();

        Admin existing = new Admin();
        existing.setId(id);
        existing.setEmail("admin@mail.com");
        existing.setRole(AdminRole.STANDARD);

        Admin update = new Admin();
        update.setId(id);
        update.setEmail("admin@mail.com");
        update.setSname("Ivanov");
        update.setFname("Ivan");
        update.setMname("Ivanovich");
        update.setPhoneNumber("+123456");

        when(adminRepository.findByEmail("admin@mail.com"))
                .thenReturn(Optional.of(existing));

        when(adminRepository.findById(id))
                .thenReturn(Optional.of(existing));

        mockSecurity(createAnotherAdmin());

        when(adminRepository.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        Admin result = service.createUpdateAdmin(update);

        assertEquals("Ivanov", result.getSname());
        assertEquals("Ivan", result.getFname());

        verify(adminRepository).save(existing);
    }

    @Test
    void updateAdmin_notSuperAndNotOwner() {

        UUID id = UUID.randomUUID();

        Admin existing = new Admin();
        existing.setId(id);
        existing.setEmail("existing@mail.com");
        existing.setRole(AdminRole.STANDARD);

        Admin update = new Admin();
        update.setId(id);
        update.setEmail("existing@mail.com");

        when(adminRepository.findByEmail("existing@mail.com"))
                .thenReturn(Optional.of(existing));

        when(adminRepository.findById(id))
                .thenReturn(Optional.of(existing));

        Admin current = new Admin();
        current.setId(UUID.randomUUID());
        current.setEmail("current@mail.com");
        current.setRole(AdminRole.STANDARD);

        mockSecurity(current);

        assertThrows(AccessDeniedException.class,
                () -> service.createUpdateAdmin(update));
    }

    @Test
    void changePassword_adminNotFound() {

        PasswordChange change = new PasswordChange();
        change.setAdminId(UUID.randomUUID());

        when(adminRepository.findById(any()))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> service.changePassword(change));
    }

    @Test
    void changePassword_notSuperAndNotOwner() {

        PasswordChange change = new PasswordChange();
        change.setAdminId(admin.getId());

        when(adminRepository.findById(admin.getId()))
                .thenReturn(Optional.of(admin));

        Admin current = new Admin();
        current.setId(UUID.randomUUID());
        current.setEmail("other@mail.com");
        current.setRole(AdminRole.STANDARD);

        mockSecurity(current);

        assertThrows(AccessDeniedException.class,
                () -> service.changePassword(change));
    }

    @Test
    void deactivateAdmin_success() {

        when(adminRepository.findById(admin.getId()))
                .thenReturn(Optional.of(admin));

        mockSecurity(createAnotherAdmin());

        service.deactivateAdmin(admin.getId());

        assertFalse(admin.getIsActive());

        verify(tokenRevocationService)
                .revokeUserTokens(admin.getId());
    }

    @Test
    void changePassword_superAdminForbidden() {

        admin.setRole(AdminRole.SUPER);

        PasswordChange change = new PasswordChange();
        change.setAdminId(admin.getId());

        when(adminRepository.findById(admin.getId()))
                .thenReturn(Optional.of(admin));

        mockSecurity(createAnotherAdmin());

        assertThrows(AccessDeniedException.class,
                () -> service.changePassword(change));
    }

    @Test
    void changePassword_oldPasswordIncorrect() {

        PasswordChange change = new PasswordChange();
        change.setAdminId(admin.getId());
        change.setOldPassword("wrong");
        change.setNewPassword("NewStrong123!");

        when(adminRepository.findById(admin.getId()))
                .thenReturn(Optional.of(admin));

        mockSecurity(admin);

        when(passwordPort.matches("wrong", admin.getPassword()))
                .thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                () -> service.changePassword(change));
    }

    @Test
    void changePassword_newPasswordWeak() {

        PasswordChange change = new PasswordChange();
        change.setAdminId(admin.getId());
        change.setOldPassword("old");
        change.setNewPassword("weak");

        when(adminRepository.findById(admin.getId()))
                .thenReturn(Optional.of(admin));

        mockSecurity(admin);

        when(passwordPort.matches("old", admin.getPassword()))
                .thenReturn(true);

        when(passwordPort.isStrong(any(), any(), any(), any()))
                .thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                () -> service.changePassword(change));
    }

    @Test
    void changePassword_samePassword() {

        PasswordChange change = new PasswordChange();
        change.setAdminId(admin.getId());
        change.setOldPassword("old");
        change.setNewPassword("old");

        when(adminRepository.findById(admin.getId()))
                .thenReturn(Optional.of(admin));

        mockSecurity(admin);

        when(passwordPort.matches("old", admin.getPassword()))
                .thenReturn(true);

        when(passwordPort.isStrong(any(), any(), any(), any()))
                .thenReturn(true);

        when(passwordPort.matches("old", admin.getPassword()))
                .thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> service.changePassword(change));
    }

    @Test
    void changePassword_success() {

        PasswordChange change = new PasswordChange();
        change.setAdminId(admin.getId());
        change.setOldPassword("old");
        change.setNewPassword("NewStrong123!");

        when(adminRepository.findById(admin.getId()))
                .thenReturn(Optional.of(admin));

        mockSecurity(admin);

        when(passwordPort.matches("old", admin.getPassword()))
                .thenReturn(true);

        when(passwordPort.isStrong(any(), any(), any(), any()))
                .thenReturn(true);

        when(passwordPort.matches("NewStrong123!", admin.getPassword()))
                .thenReturn(false);

        when(passwordPort.encode(any()))
                .thenReturn("encodedNew");

        service.changePassword(change);

        assertEquals("encodedNew", admin.getPassword());

        verify(adminRepository).save(admin);
        verify(tokenRevocationService).revokeUserTokens(admin.getId());
    }

    @Test
    void activateAdmin_notFound() {

        when(adminRepository.findById(any()))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> service.activateAdmin(UUID.randomUUID()));
    }

    @Test
    void activateAdmin_alreadyActive() {

        admin.setIsActive(true);

        when(adminRepository.findById(admin.getId()))
                .thenReturn(Optional.of(admin));

        assertThrows(IllegalArgumentException.class,
                () -> service.activateAdmin(admin.getId()));
    }

    @Test
    void activateAdmin_success() {

        admin.setIsActive(false);

        when(adminRepository.findById(admin.getId()))
                .thenReturn(Optional.of(admin));

        service.activateAdmin(admin.getId());

        assertTrue(admin.getIsActive());

        verify(adminRepository).save(admin);
        verify(tokenRevocationService)
                .revokeUserTokens(admin.getId());
    }

    @Test
    void deactivateAdmin_notFound() {

        when(adminRepository.findById(any()))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> service.deactivateAdmin(UUID.randomUUID()));
    }

    @Test
    void deactivateAdmin_superForbidden() {

        admin.setRole(AdminRole.SUPER);

        when(adminRepository.findById(admin.getId()))
                .thenReturn(Optional.of(admin));

        assertThrows(AccessDeniedException.class,
                () -> service.deactivateAdmin(admin.getId()));
    }

    @Test
    void deactivateAdmin_self() {

        when(adminRepository.findById(admin.getId()))
                .thenReturn(Optional.of(admin));

        mockSecurity(admin);

        assertThrows(IllegalArgumentException.class,
                () -> service.deactivateAdmin(admin.getId()));
    }

    @Test
    void deactivateAdmin_alreadyDeactivated() {

        admin.setIsActive(false);

        when(adminRepository.findById(admin.getId()))
                .thenReturn(Optional.of(admin));

        assertThrows(IllegalArgumentException.class,
                () -> service.deactivateAdmin(admin.getId()));
    }

    @Test
    void changeRole_adminNotFound() {

        when(adminRepository.findById(any()))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> service.changeRole(UUID.randomUUID(), AdminRole.STANDARD));
    }

    @Test
    void changeRole_adminNotActive() {

        admin.setIsActive(false);

        when(adminRepository.findById(admin.getId()))
                .thenReturn(Optional.of(admin));

        assertThrows(IllegalArgumentException.class,
                () -> service.changeRole(admin.getId(), AdminRole.SUPER));
    }

    @Test
    void changeRole_selfForbidden() {

        when(adminRepository.findById(admin.getId()))
                .thenReturn(Optional.of(admin));

        mockSecurity(admin);

        assertThrows(IllegalArgumentException.class,
                () -> service.changeRole(admin.getId(), AdminRole.SUPER));
    }

    @Test
    void changeRole_maxSuperExceeded() {

        admin.setRole(AdminRole.STANDARD);

        when(adminRepository.findById(admin.getId()))
                .thenReturn(Optional.of(admin));

        when(adminRepository.findByFilter(any()))
                .thenReturn(List.of(new Admin(), new Admin()));

        service.setMaxSuperAdmins(2);

        mockSecurity(createAnotherAdmin());

        assertThrows(IllegalArgumentException.class,
                () -> service.changeRole(admin.getId(), AdminRole.SUPER));
    }

    @Test
    void changeRole_sameRole() {

        admin.setRole(AdminRole.STANDARD);

        when(adminRepository.findById(admin.getId()))
                .thenReturn(Optional.of(admin));

        mockSecurity(createAnotherAdmin());

        assertThrows(IllegalArgumentException.class,
                () -> service.changeRole(admin.getId(), AdminRole.STANDARD));
    }

    @Test
    void changeRole_successToSuper() {

        admin.setRole(AdminRole.STANDARD);

        when(adminRepository.findById(admin.getId()))
                .thenReturn(Optional.of(admin));

        when(adminRepository.findByFilter(any()))
                .thenReturn(List.of()); // нет супер-админов

        service.setMaxSuperAdmins(2);

        mockSecurity(createAnotherAdmin());

        service.changeRole(admin.getId(), AdminRole.SUPER);

        assertEquals(AdminRole.SUPER, admin.getRole());

        verify(adminRepository).save(admin);
        verify(tokenRevocationService)
                .revokeUserTokens(admin.getId());
    }

    @Test
    void changeRole_successToStandard() {

        admin.setRole(AdminRole.SUPER);

        when(adminRepository.findById(admin.getId()))
                .thenReturn(Optional.of(admin));

        mockSecurity(createAnotherAdmin());

        service.changeRole(admin.getId(), AdminRole.STANDARD);

        assertEquals(AdminRole.STANDARD, admin.getRole());

        verify(adminRepository).save(admin);
        verify(tokenRevocationService)
                .revokeUserTokens(admin.getId());
    }

    private void mockSecurity(Admin admin) {

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(admin.getEmail());

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);

        SecurityContextHolder.setContext(context);

        when(adminRepository.findByEmail(admin.getEmail()))
                .thenReturn(Optional.of(admin));
    }

    private Admin createAnotherAdmin() {
        Admin a = new Admin();
        a.setId(UUID.randomUUID());
        a.setEmail("another@mail.com");
        a.setRole(AdminRole.SUPER);
        a.setIsActive(true);
        return a;
    }
}
