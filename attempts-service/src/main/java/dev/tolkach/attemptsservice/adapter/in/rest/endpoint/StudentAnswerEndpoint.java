package dev.tolkach.attemptsservice.adapter.in.rest.endpoint;

import dev.tolkach.attemptsservice.adapter.in.rest.dto.StudentAnswerDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface StudentAnswerEndpoint {
    @PostMapping(ApiEndpoints.StudentAnswer.SEARCH)
    @PreAuthorize("permitAll()")
    ResponseEntity<List<StudentAnswerDto>> getStudentAnswers(
            @RequestBody StudentAnswerDto filter
    );

    @PostMapping(ApiEndpoints.StudentAnswer.BASE)
    @PreAuthorize("#p0.id == null or (isAuthenticated() and hasAuthority('ROLE_SUPER'))")
    ResponseEntity<StudentAnswerDto> createUpdateStudentAnswer(
            @Valid @RequestBody StudentAnswerDto dto
    );

    @DeleteMapping(ApiEndpoints.StudentAnswer.BY_ID)
    @PreAuthorize("hasAuthority('ROLE_SUPER')")
    ResponseEntity<Void> deleteStudentAnswer(
            @PathVariable UUID studentAnswerId
    );
}
