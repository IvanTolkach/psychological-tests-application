package dev.tolkach.testsservice.adapter.in.rest.endpoint;

import dev.tolkach.testsservice.adapter.in.rest.dto.AnswerOptionDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public interface AnswerOptionEndpoint {
    @PostMapping(ApiEndpoints.AnswerOption.SEARCH)
    @PreAuthorize("permitAll()")
    ResponseEntity<List<AnswerOptionDto>> getAnswerOptions(@RequestBody AnswerOptionDto filter);

    @GetMapping(ApiEndpoints.AnswerOption.BY_ID)
    @PreAuthorize("permitAll()")
    ResponseEntity<AnswerOptionDto> getAnswerOptionById(@PathVariable UUID answerOptionId);

    @PostMapping(ApiEndpoints.AnswerOption.BASE)
    @PreAuthorize("hasAuthority('ROLE_SUPER')")
    ResponseEntity<AnswerOptionDto> createUpdateAnswerOption(@Valid @RequestBody AnswerOptionDto dto);

    @DeleteMapping(ApiEndpoints.AnswerOption.BY_ID)
    @PreAuthorize("hasAuthority('ROLE_SUPER')")
    ResponseEntity<Void> deleteAnswerOption(@PathVariable UUID answerOptionId);
}
