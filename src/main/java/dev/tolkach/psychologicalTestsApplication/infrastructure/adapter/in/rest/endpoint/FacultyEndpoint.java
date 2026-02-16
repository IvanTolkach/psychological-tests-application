package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.endpoint;

import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto.FacultyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface FacultyEndpoint {
    @PostMapping(ApiEndpoints.Faculty.SEARCH)
    ResponseEntity<List<FacultyDto>> getFaculties(
            @RequestBody FacultyDto filter
    );
}
