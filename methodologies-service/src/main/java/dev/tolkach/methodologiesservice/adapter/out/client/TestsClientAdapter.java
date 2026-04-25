package dev.tolkach.methodologiesservice.adapter.out.client;

import dev.tolkach.methodologiesservice.application.port.out.TestsPort;
import feign.FeignException;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.UUID;

@Component
public class TestsClientAdapter implements TestsPort {

    private final TestsClient testsClient;

    public TestsClientAdapter(TestsClient testsClient) {
        this.testsClient = testsClient;
    }

    @Override
    public void validateQuestionExists(UUID questionId) {
        try {
            testsClient.getQuestion(questionId);
        } catch (FeignException.NotFound e) {
            throw new NoSuchElementException("Question not found with id: " + questionId);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}