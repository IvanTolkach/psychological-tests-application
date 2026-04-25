package dev.tolkach.usersservice.adapter.in.rest.endpoint;

import dev.tolkach.usersservice.adapter.in.rest.dto.FacultyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface FacultyEndpoint {
    @PostMapping(ApiEndpoints.Faculty.SEARCH)
    @PreAuthorize("permitAll()")
    ResponseEntity<List<FacultyDto>> getFaculties(@RequestBody FacultyDto filter);
}
