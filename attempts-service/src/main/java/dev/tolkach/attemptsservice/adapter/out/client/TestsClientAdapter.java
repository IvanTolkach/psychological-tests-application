package dev.tolkach.attemptsservice.adapter.out.client;

import dev.tolkach.attemptsservice.application.port.out.TestsPort;
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
        Object response = testsClient.getQuestion(questionId);
        if (response == null) {
            throw new NoSuchElementException("Question not found with id: " + questionId);
        }
    }

    @Override
    public void validateTestExists(UUID testId) {
        Object response = testsClient.getTest(testId);
        if (response == null) {
            throw new NoSuchElementException("Test not found with id: " + testId);
        }
    }

    @Override
    public void validateAnswerOptionExists(UUID answerOptionId) {
        Object response = testsClient.getAnswerOption(answerOptionId);
        if (response == null) {
            throw new NoSuchElementException("AnswerOption not found with id: " + answerOptionId);
        }
    }
}
