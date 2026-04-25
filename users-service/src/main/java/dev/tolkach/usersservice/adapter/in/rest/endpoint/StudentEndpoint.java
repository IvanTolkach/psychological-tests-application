package dev.tolkach.usersservice.adapter.in.rest.endpoint;

import dev.tolkach.usersservice.adapter.in.rest.dto.StudentDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public interface StudentEndpoint {
    @PostMapping(ApiEndpoints.Student.SEARCH)
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<List<StudentDto>> getStudents(@RequestBody StudentDto filter);

    @GetMapping(ApiEndpoints.Student.BY_ID)
    @PreAuthorize("permitAll()")
    ResponseEntity<StudentDto> getStudentById(@PathVariable UUID studentId);

    @PostMapping(ApiEndpoints.Student.BASE)
    @PreAuthorize("#p0.id == null or isAuthenticated()")
    ResponseEntity<StudentDto> createUpdateStudent(@Valid @RequestBody StudentDto studentDto);

    @DeleteMapping(ApiEndpoints.Student.BY_ID)
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<Void> deleteStudent(@PathVariable UUID studentId);
}
