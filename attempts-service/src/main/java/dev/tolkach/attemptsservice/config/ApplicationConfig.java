package dev.tolkach.attemptsservice.config;

import dev.tolkach.attemptsservice.application.port.in.ReportUseCase;
import dev.tolkach.attemptsservice.application.port.in.StudentAnswerUseCase;
import dev.tolkach.attemptsservice.application.port.in.TestAttemptScoreUseCase;
import dev.tolkach.attemptsservice.application.port.in.TestAttemptUseCase;
import dev.tolkach.attemptsservice.application.port.out.*;
import dev.tolkach.attemptsservice.application.service.ReportService;
import dev.tolkach.attemptsservice.application.service.StudentAnswerService;
import dev.tolkach.attemptsservice.application.service.TestAttemptScoreService;
import dev.tolkach.attemptsservice.application.service.TestAttemptService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public StudentAnswerUseCase studentAnswerUseCase(StudentAnswerRepository studentAnswerRepository, TestAttemptRepository testAttemptRepository, TestsPort testsPort) {
        return new StudentAnswerService(studentAnswerRepository, testAttemptRepository, testsPort);
    }

    @Bean
    public TestAttemptScoreUseCase testAttemptScoreUseCase(TestAttemptScoreRepository testAttemptScoreRepository, TestAttemptRepository testAttemptRepository, MethodologiesPort methodologiesPort, StudentAnswerRepository studentAnswerRepository) {
        return new TestAttemptScoreService(testAttemptScoreRepository, testAttemptRepository, methodologiesPort, studentAnswerRepository);
    }

    @Bean
    public TestAttemptUseCase testAttemptUseCase(TestAttemptRepository testAttemptRepository, UsersPort usersPort, TestsPort testsPort) {
        return new TestAttemptService(testAttemptRepository, usersPort, testsPort);
    }

    @Bean
    public ReportUseCase reportUseCase(TestAttemptRepository testAttemptRepository, StudentAnswerRepository studentAnswerRepository, TestsPort testsPort, UsersPort studentsPort) {
        return new ReportService(testAttemptRepository, studentAnswerRepository, testsPort, studentsPort);
    }
}
