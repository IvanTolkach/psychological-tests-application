package dev.tolkach.psychologicalTestsApplication.infrastructure.config;

import dev.tolkach.psychologicalTestsApplication.application.service.StudentService;
import dev.tolkach.psychologicalTestsApplication.domain.port.in.StudentUseCase;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.FacultyRepository;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.StudentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public StudentUseCase studentUseCase(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        return new StudentService(studentRepository, facultyRepository);
    }
}
