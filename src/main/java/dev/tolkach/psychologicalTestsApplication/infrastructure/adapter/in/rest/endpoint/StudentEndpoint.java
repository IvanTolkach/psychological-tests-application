package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.endpoint;

import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto.StudentDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public interface StudentEndpoint {
    @PostMapping(ApiEndpoints.Student.SEARCH)
    ResponseEntity<List<StudentDto>> getStudents(
            @RequestBody StudentDto filter
    );

    @PostMapping(ApiEndpoints.Student.BASE)
    ResponseEntity<StudentDto> createUpdateStudent(
            @Valid @RequestBody StudentDto studentDto
    );

    @DeleteMapping(ApiEndpoints.Student.BY_ID)
    ResponseEntity<Void> deleteStudent(
            @PathVariable UUID studentId
    );
}
