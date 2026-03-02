package dev.tolkach.methodologiesservice.adapter.in.rest.endpoint;

import dev.tolkach.methodologiesservice.adapter.in.rest.dto.ScaleQuestionDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface ScaleQuestionEndpoint {
    @PostMapping(ApiEndpoints.ScaleQuestion.SEARCH)
    ResponseEntity<List<ScaleQuestionDto>> getScaleQuestions(@RequestBody ScaleQuestionDto filter);

    @PostMapping(ApiEndpoints.ScaleQuestion.BASE)
    ResponseEntity<ScaleQuestionDto> createUpdateScaleQuestion(@Valid @RequestBody ScaleQuestionDto dto);

    @DeleteMapping(ApiEndpoints.ScaleQuestion.BY_ID)
    ResponseEntity<Void> deleteScaleQuestion(@PathVariable UUID scaleQuestionId);
}
