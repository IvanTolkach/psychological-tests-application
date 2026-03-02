package dev.tolkach.testsservice.adapter.in.rest.endpoint;

import dev.tolkach.testsservice.adapter.in.rest.dto.AnswerOptionDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public interface AnswerOptionEndpoint {
    @PostMapping(ApiEndpoints.AnswerOption.SEARCH)
    ResponseEntity<List<AnswerOptionDto>> getAnswerOptions(@RequestBody AnswerOptionDto filter);

    @GetMapping(ApiEndpoints.AnswerOption.BY_ID)
    ResponseEntity<AnswerOptionDto> getAnswerOptionById(@PathVariable UUID answerOptionId);

    @PostMapping(ApiEndpoints.AnswerOption.BASE)
    ResponseEntity<AnswerOptionDto> createUpdateAnswerOption(@Valid @RequestBody AnswerOptionDto dto);

    @DeleteMapping(ApiEndpoints.AnswerOption.BY_ID)
    ResponseEntity<Void> deleteAnswerOption(@PathVariable UUID answerOptionId);
}
