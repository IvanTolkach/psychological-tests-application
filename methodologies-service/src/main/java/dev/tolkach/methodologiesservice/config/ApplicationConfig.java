package dev.tolkach.methodologiesservice.config;

import dev.tolkach.methodologiesservice.application.port.in.MethodologyUseCase;
import dev.tolkach.methodologiesservice.application.port.in.ScaleQuestionUseCase;
import dev.tolkach.methodologiesservice.application.port.in.ScaleUseCase;
import dev.tolkach.methodologiesservice.application.port.in.ScoreRangeUseCase;
import dev.tolkach.methodologiesservice.application.port.out.*;
import dev.tolkach.methodologiesservice.application.service.MethodologyService;
import dev.tolkach.methodologiesservice.application.service.ScaleQuestionService;
import dev.tolkach.methodologiesservice.application.service.ScaleService;
import dev.tolkach.methodologiesservice.application.service.ScoreRangeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class ApplicationConfig {

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
    public ScaleQuestionUseCase scaleQuestionUseCase(ScaleQuestionRepository scaleQuestionRepository, ScaleRepository scaleRepository, TestsPort testsPort) {
        return new ScaleQuestionService(scaleQuestionRepository, scaleRepository, testsPort);
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        return new InMemoryUserDetailsManager();
    }
}
