package dev.tolkach.attemptsservice.application.service;

import common.dto.*;
import dev.tolkach.attemptsservice.application.model.StudentAnswer;
import dev.tolkach.attemptsservice.application.model.TestAttempt;
import dev.tolkach.attemptsservice.application.model.TestAttemptScore;
import dev.tolkach.attemptsservice.application.port.in.TestAttemptScoreUseCase;
import dev.tolkach.attemptsservice.application.port.out.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.function.UnaryOperator.identity;

public class TestAttemptScoreService implements TestAttemptScoreUseCase {

    private final TestAttemptScoreRepository testAttemptScoreRepository;
    private final TestAttemptRepository testAttemptRepository;
    private final MethodologiesPort methodologiesPort;
    private final TestsPort testsPort;
    private final StudentAnswerRepository studentAnswerRepository;

    public TestAttemptScoreService(TestAttemptScoreRepository testAttemptScoreRepository, TestAttemptRepository testAttemptRepository, MethodologiesPort methodologiesPort, TestsPort testsPort, StudentAnswerRepository studentAnswerRepository) {
        this.testAttemptScoreRepository = testAttemptScoreRepository;
        this.testAttemptRepository = testAttemptRepository;
        this.methodologiesPort = methodologiesPort;
        this.testsPort = testsPort;
        this.studentAnswerRepository = studentAnswerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestAttemptScore> getTestAttemptScoresByFilter(TestAttemptScore filter) {
        if (filter.getTestAttemptId() != null) {
            testAttemptRepository.findById(filter.getTestAttemptId())
                    .orElseThrow(() -> new NoSuchElementException("TestAttempt not found with id: " + filter.getTestAttemptId()));
        }
        if (filter.getScaleId() != null) {
            methodologiesPort.validateScaleExists(filter.getScaleId());
        }
        return testAttemptScoreRepository.findByFilter(filter);
    }

    @Override
    @Transactional
    public TestAttemptScore createUpdateTestAttemptScore(TestAttemptScore testAttemptScore) {
        testAttemptRepository.findById(testAttemptScore.getTestAttemptId())
                .orElseThrow(() -> new NoSuchElementException("TestAttempt not found with id: " + testAttemptScore.getTestAttemptId()));

        methodologiesPort.validateScaleExists(testAttemptScore.getScaleId());

        TestAttemptScore checkFilter = new TestAttemptScore();
        checkFilter.setTestAttemptId(testAttemptScore.getTestAttemptId());
        checkFilter.setScaleId(testAttemptScore.getScaleId());

        List<TestAttemptScore> existingScores = testAttemptScoreRepository.findByFilter(checkFilter);

        boolean alreadyScored = existingScores.stream()
                .anyMatch(s -> !s.getId().equals(testAttemptScore.getId()));

        if (alreadyScored) {
            throw new IllegalArgumentException(
                    "Score for scale " + testAttemptScore.getScaleId() +
                            " already exists in attempt " + testAttemptScore.getTestAttemptId()
            );
        }

        if (testAttemptScore.getId() == null) {
            TestAttemptScore calculated = calculateScoreForScale(
                    testAttemptScore.getTestAttemptId(),
                    testAttemptScore.getScaleId()
            );

            return testAttemptScoreRepository.save(calculated);
        } else {
            TestAttemptScore existing = testAttemptScoreRepository.findById(testAttemptScore.getId())
                    .orElseThrow(() -> new NoSuchElementException("TestAttemptScore not found with id: " + testAttemptScore.getId()));

            existing.setTestAttemptId(testAttemptScore.getTestAttemptId());
            existing.setScaleId(testAttemptScore.getScaleId());
            existing.setScore(testAttemptScore.getScore());
            existing.setInterpretation(testAttemptScore.getInterpretation());

            return testAttemptScoreRepository.save(existing);
        }
    }

    @Override
    @Transactional
    public void deleteTestAttemptScore(UUID id) {
        if (testAttemptScoreRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException("TestAttemptScore not found with id: " + id);
        }
        testAttemptScoreRepository.deleteById(id);
    }

    @Transactional
    public TestAttemptScore calculateScoreForScale(UUID testAttemptId, UUID scaleId) {
        TestAttempt attempt = testAttemptRepository.findById(testAttemptId)
                .orElseThrow(() -> new NoSuchElementException("Attempt not found"));


        TestDto test = testsPort.getTestById(attempt.getTestId());

        ScaleDto scale = methodologiesPort.getScalesByMethodologyId(test.getMethodologyId())
                .stream()
                .filter(s -> s.getId().equals(scaleId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Scale not found: " + scaleId));


        List<UUID> questionIds;
        if (Boolean.TRUE.equals(scale.getIsTotal())) {
            questionIds = testsPort.getQuestionsByTestId(test.getId())
                    .stream().map(QuestionDto::getId).collect(Collectors.toList());
        } else {
            SearchFilterDto filter = new SearchFilterDto();
            filter.setScaleId(scaleId);
            List<ScaleQuestionDto> scaleQuestions = methodologiesPort.getScaleQuestionsByScaleIds(List.of(scaleId));
            questionIds = scaleQuestions.stream().map(ScaleQuestionDto::getQuestionId).collect(Collectors.toList());
        }


        StudentAnswer ansFilter = new StudentAnswer();
        ansFilter.setTestAttemptId(testAttemptId);
        List<StudentAnswer> answers = studentAnswerRepository.findByFilter(ansFilter);

        Map<UUID, StudentAnswer> answerMap = answers.stream()
                .collect(Collectors.toMap(StudentAnswer::getQuestionId, identity()));


        List<AnswerOptionDto> allOptions = testsPort.getAnswerOptionsByQuestionIds(questionIds);

        Map<UUID, List<AnswerOptionDto>> optionMap = allOptions.stream()
                .collect(Collectors.groupingBy(AnswerOptionDto::getQuestionId));

        int score = 0;

        for (UUID qId : questionIds) {
            StudentAnswer answer = answerMap.get(qId);
            if (answer == null) {
                continue;
            }

            int qScore = 0;

            if (answer.getAnswerOptionId() != null) {
                List<AnswerOptionDto> options = optionMap.get(qId);
                if (options != null) {
                    AnswerOptionDto selected = options.stream()
                            .filter(o -> o.getId().equals(answer.getAnswerOptionId()))
                            .findFirst()
                            .orElse(null);
                    if (selected != null) {
                        qScore = selected.getScore() != null ? selected.getScore() : 0;
                    }
                }
            }
            else if (answer.getAnswerValue() != null) {
                try {
                    qScore = Integer.parseInt(answer.getAnswerValue().trim());
                }
                catch (Exception e) {

                }
            }

            score += qScore;
        }


        List<ScoreRangeDto> ranges = methodologiesPort.getScoreRangesByScaleIds(List.of(scaleId));

        String interpretation = "undefined";

        for (ScoreRangeDto r : ranges) {
            if (score >= r.getMinScore() && score <= r.getMaxScore()) {
                interpretation = r.getInterpretation();
                break;
            }
        }

        TestAttemptScore result = new TestAttemptScore();
        result.setTestAttemptId(testAttemptId);
        result.setScaleId(scaleId);
        result.setScore(score);
        result.setInterpretation(interpretation);

        return result;
    }
}
