package dev.tolkach.psychologicalTestsApplication.infrastructure.config;

import dev.tolkach.psychologicalTestsApplication.application.service.AdminService;
import dev.tolkach.psychologicalTestsApplication.application.service.FacultyService;
import dev.tolkach.psychologicalTestsApplication.application.service.StudentService;
import dev.tolkach.psychologicalTestsApplication.domain.port.in.AdminUseCase;
import dev.tolkach.psychologicalTestsApplication.domain.port.in.FacultyUseCase;
import dev.tolkach.psychologicalTestsApplication.domain.port.in.StudentUseCase;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.AdminRepository;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.FacultyRepository;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.StudentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {

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
    public AdminUseCase adminUseCase(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        return new AdminService(adminRepository, passwordEncoder);
    }
}
