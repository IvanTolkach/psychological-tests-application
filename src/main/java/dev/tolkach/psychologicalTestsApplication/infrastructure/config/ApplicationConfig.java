package dev.tolkach.psychologicalTestsApplication.infrastructure.config;

import dev.tolkach.psychologicalTestsApplication.application.service.*;
import dev.tolkach.psychologicalTestsApplication.domain.port.in.*;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.*;
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

    @Bean
    public MethodologyUseCase methodologyUseCase(MethodologyRepository methodologyRepository) {
        return new MethodologyService(methodologyRepository);
    }

    @Bean
    public ScaleUseCase scaleUseCase(ScaleRepository scaleRepository, MethodologyRepository methodologyRepository) {
        return new ScaleService(scaleRepository, methodologyRepository);
    }

    @Bean
    public ScoreRangeUseCase scoreRangeUseCase(ScoreRangeRepository scoreRangeRepository, ScaleRepository scaleRepository) {
        return new ScoreRangeService(scoreRangeRepository, scaleRepository);
    }

    @Bean
    public TestUseCase testUseCase(TestRepository testRepository, MethodologyRepository methodologyRepository, AdminRepository adminRepository) {
        return new TestService(testRepository, methodologyRepository, adminRepository);
    }

    @Bean
    public QuestionUseCase questionUseCase(QuestionRepository questionRepository, TestRepository testRepository) {
        return new QuestionService(questionRepository, testRepository);
    }

    @Bean
    public AnswerOptionUseCase answerOptionUseCase(AnswerOptionRepository answerOptionRepository, QuestionRepository questionRepository) {
        return new AnswerOptionService(answerOptionRepository, questionRepository);
    }

    @Bean
    public ScaleQuestionUseCase scaleQuestionUseCase(ScaleQuestionRepository scaleQuestionRepository, ScaleRepository scaleRepository, QuestionRepository questionRepository) {
        return new ScaleQuestionService(scaleQuestionRepository, scaleRepository, questionRepository);
    }

    @Bean
    public TestAttemptUseCase testAttemptUseCase(TestAttemptRepository testAttemptRepository, StudentRepository studentRepository, TestRepository testRepository) {
        return new TestAttemptService(testAttemptRepository, studentRepository, testRepository);
    }

    @Bean
    public StudentAnswerUseCase studentAnswerUseCase(StudentAnswerRepository studentAnswerRepository, TestAttemptRepository testAttemptRepository, QuestionRepository questionRepository, AnswerOptionRepository answerOptionRepository) {
        return new StudentAnswerService(studentAnswerRepository, testAttemptRepository, questionRepository, answerOptionRepository);
    }
}
