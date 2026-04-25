package dev.tolkach.methodologiesservice.adapter.in.rest.endpoint;

import dev.tolkach.methodologiesservice.adapter.in.rest.dto.ScaleQuestionDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface ScaleQuestionEndpoint {
    @PostMapping(ApiEndpoints.ScaleQuestion.SEARCH)
    @PreAuthorize("permitAll()")
    ResponseEntity<List<ScaleQuestionDto>> getScaleQuestions(@RequestBody ScaleQuestionDto filter);

    @PostMapping(ApiEndpoints.ScaleQuestion.BASE)
    @PreAuthorize("hasAuthority('ROLE_SUPER')")
    ResponseEntity<ScaleQuestionDto> createUpdateScaleQuestion(@Valid @RequestBody ScaleQuestionDto dto);

    @DeleteMapping(ApiEndpoints.ScaleQuestion.BY_ID)
    @PreAuthorize("hasAuthority('ROLE_SUPER')")
    ResponseEntity<Void> deleteScaleQuestion(@PathVariable UUID scaleQuestionId);
}
