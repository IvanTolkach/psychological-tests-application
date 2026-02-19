package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.endpoint;

import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto.StudentAnswerDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface StudentAnswerEndpoint {
    @PostMapping(ApiEndpoints.StudentAnswer.SEARCH)
    ResponseEntity<List<StudentAnswerDto>> getStudentAnswers(
            @RequestBody StudentAnswerDto filter
    );

    @PostMapping(ApiEndpoints.StudentAnswer.BASE)
    ResponseEntity<StudentAnswerDto> createUpdateStudentAnswer(
            @Valid @RequestBody StudentAnswerDto dto
    );

    @DeleteMapping(ApiEndpoints.StudentAnswer.BY_ID)
    ResponseEntity<Void> deleteStudentAnswer(
            @PathVariable UUID studentAnswerId
    );
}
