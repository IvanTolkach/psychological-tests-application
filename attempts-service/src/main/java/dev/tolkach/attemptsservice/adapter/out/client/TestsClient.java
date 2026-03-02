package dev.tolkach.attemptsservice.adapter.out.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "tests-service")
public interface TestsClient {

    @GetMapping("/api/tests/{id}")
    Object getTest(@PathVariable UUID id);

    @GetMapping("/api/questions/{id}")
    Object getQuestion(@PathVariable UUID id);

    @GetMapping("/api/answer-options/{id}")
    Object getAnswerOption(@PathVariable UUID id);
}
