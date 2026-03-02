package dev.tolkach.testsservice.config;

import dev.tolkach.testsservice.application.port.in.AnswerOptionUseCase;
import dev.tolkach.testsservice.application.port.in.QuestionUseCase;
import dev.tolkach.testsservice.application.port.in.TestUseCase;
import dev.tolkach.testsservice.application.port.out.*;
import dev.tolkach.testsservice.application.service.AnswerOptionService;
import dev.tolkach.testsservice.application.service.QuestionService;
import dev.tolkach.testsservice.application.service.TestService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public TestUseCase testUseCase(TestRepository testRepository, MethodologiesPort methodologiesPort, UsersPort usersPort) {
        return new TestService(testRepository, methodologiesPort, usersPort);
    }

    @Bean
    public QuestionUseCase questionUseCase(QuestionRepository questionRepository, TestRepository testRepository) {
        return new QuestionService(questionRepository, testRepository);
    }

    @Bean
    public AnswerOptionUseCase answerOptionUseCase(AnswerOptionRepository answerOptionRepository, QuestionRepository questionRepository) {
        return new AnswerOptionService(answerOptionRepository, questionRepository);
    }
}
