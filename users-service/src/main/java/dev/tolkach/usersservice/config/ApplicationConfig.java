package dev.tolkach.usersservice.config;

import dev.tolkach.usersservice.application.port.in.AdminUseCase;
import dev.tolkach.usersservice.application.port.in.AuthUseCase;
import dev.tolkach.usersservice.application.port.in.FacultyUseCase;
import dev.tolkach.usersservice.application.port.in.StudentUseCase;
import dev.tolkach.usersservice.application.port.out.*;
import dev.tolkach.usersservice.application.service.AdminService;
import dev.tolkach.usersservice.application.service.AuthService;
import dev.tolkach.usersservice.application.service.FacultyService;
import dev.tolkach.usersservice.application.service.StudentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {

    @Bean
    public UserDetailsService userDetailsService(AdminUseCase adminUseCase) {
        return adminUseCase::getAdminByEmail;
    }

    @Bean
    public StudentUseCase studentUseCase(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        return new StudentService(studentRepository, facultyRepository);
    }

    @Bean
    public FacultyUseCase facultyUseCase(FacultyRepository facultyRepository) {
        return new FacultyService(facultyRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AdminUseCase adminUseCase(AdminRepository adminRepository, PasswordPort passwordPort) {
        return new AdminService(adminRepository, passwordPort);
    }

    @Bean
    public AuthUseCase authUseCase(AdminUseCase adminUseCase, JwtPort jwtPort, AuthenticationManager authenticationManager) {
        return new AuthService(adminUseCase, jwtPort, authenticationManager);
    }
}
