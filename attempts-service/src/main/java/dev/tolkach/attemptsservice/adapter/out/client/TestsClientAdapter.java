package dev.tolkach.attemptsservice.adapter.out.client;

import common.dto.AnswerOptionDto;
import common.dto.QuestionDto;
import common.dto.SearchFilterDto;
import common.dto.TestDto;
import dev.tolkach.attemptsservice.application.port.out.TestsPort;
import feign.FeignException;
import org.springframework.stereotype.Component;

import java.util.*;

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
        } catch (FeignException.NotFound | FeignException.Forbidden e) {
            throw new NoSuchElementException("Question not found with id: " + questionId);
        }
    }

    @Override
    public void validateTestExists(UUID testId) {
        try {
            testsClient.getTest(testId);
        } catch (FeignException e) {
            throw new NoSuchElementException("Test not found with id: " + testId);
        }
    }

    @Override
    public void validateAnswerOptionExists(UUID answerOptionId) {
        try {
            testsClient.getAnswerOption(answerOptionId);
        } catch (FeignException e) {
            throw new NoSuchElementException("AnswerOption not found with id: " + answerOptionId);
        }
    }

    @Override
    public TestDto getTestById(UUID testId) {
        return testsClient.getTestById(testId);
    }

    @Override
    public List<QuestionDto> getQuestionsByTestId(UUID testId) {
        SearchFilterDto filter = new SearchFilterDto();
        filter.setTestId(testId);
        return testsClient.searchQuestions(filter);
    }

    @Override
    public List<AnswerOptionDto> getAnswerOptionsByQuestionIds(Collection<UUID> questionIds) {
        List<AnswerOptionDto> result = new ArrayList<>();
        for (UUID qId : questionIds) {
            SearchFilterDto filter = new SearchFilterDto();
            filter.setQuestionId(qId);
            result.addAll(testsClient.searchAnswerOptions(filter));
        }
        return result;
    }
}
