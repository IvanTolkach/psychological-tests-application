package dev.tolkach.attemptsservice.adapter.out.client;

import common.dto.AnswerOptionDto;
import common.dto.QuestionDto;
import common.dto.SearchFilterDto;
import common.dto.TestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "tests-service")
public interface TestsClient {

    @GetMapping("/api/tests/{id}")
    Object getTest(@PathVariable UUID id);

    @GetMapping("/api/questions/{id}")
    Object getQuestion(@PathVariable UUID id);

    @GetMapping("/api/answer-options/{id}")
    Object getAnswerOption(@PathVariable UUID id);

    @GetMapping("/api/tests/{id}")
    TestDto getTestById(@PathVariable("id") UUID testId);

    @PostMapping("/api/questions/search")
    List<QuestionDto> searchQuestions(@RequestBody SearchFilterDto filter);

    @PostMapping("/api/answer-options/search")
    List<AnswerOptionDto> searchAnswerOptions(@RequestBody SearchFilterDto filter);
}
