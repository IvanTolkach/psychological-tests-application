package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.endpoint;

import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto.AnswerOptionDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface AnswerOptionEndpoint {
    @PostMapping(ApiEndpoints.AnswerOption.SEARCH)
    ResponseEntity<List<AnswerOptionDto>> getAnswerOptions(@RequestBody AnswerOptionDto filter);

    @PostMapping(ApiEndpoints.AnswerOption.BASE)
    ResponseEntity<AnswerOptionDto> createUpdateAnswerOption(@Valid @RequestBody AnswerOptionDto dto);

    @DeleteMapping(ApiEndpoints.AnswerOption.BY_ID)
    ResponseEntity<Void> deleteAnswerOption(@PathVariable UUID answerOptionId);
}
