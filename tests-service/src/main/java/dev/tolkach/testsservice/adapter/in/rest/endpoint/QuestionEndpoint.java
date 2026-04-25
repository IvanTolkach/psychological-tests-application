package dev.tolkach.testsservice.adapter.in.rest.endpoint;

import dev.tolkach.testsservice.adapter.in.rest.dto.QuestionDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public interface QuestionEndpoint {
    @PostMapping(ApiEndpoints.Question.SEARCH)
    @PreAuthorize("permitAll()")
    ResponseEntity<List<QuestionDto>> getQuestions(@RequestBody QuestionDto filter);

    @GetMapping(ApiEndpoints.Question.BY_ID)
    @PreAuthorize("permitAll()")
    ResponseEntity<QuestionDto> getQuestionById(@PathVariable UUID questionId);

    @PostMapping(ApiEndpoints.Question.BASE)
    @PreAuthorize("hasAuthority('ROLE_SUPER')")
    ResponseEntity<QuestionDto> createUpdateQuestion(@Valid @RequestBody QuestionDto dto);

    @DeleteMapping(ApiEndpoints.Question.BY_ID)
    @PreAuthorize("hasAuthority('ROLE_SUPER')")
    ResponseEntity<Void> deleteQuestion(@PathVariable UUID questionId);
}
