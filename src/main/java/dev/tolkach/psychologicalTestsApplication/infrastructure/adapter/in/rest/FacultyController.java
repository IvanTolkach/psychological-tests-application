package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest;

import dev.tolkach.psychologicalTestsApplication.domain.model.Faculty;
import dev.tolkach.psychologicalTestsApplication.domain.port.in.FacultyUseCase;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto.FacultyDto;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.endpoint.FacultyEndpoint;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.mapper.FacultyDtoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FacultyController implements FacultyEndpoint {

    private final FacultyUseCase facultyUseCase;
    private final FacultyDtoMapper facultyDtoMapper;

    public FacultyController(FacultyUseCase facultyUseCase, FacultyDtoMapper facultyDtoMapper) {
        this.facultyUseCase = facultyUseCase;
        this.facultyDtoMapper = facultyDtoMapper;
    }

    @Override
    public ResponseEntity<List<FacultyDto>> getFaculties(FacultyDto filter) {
        Faculty facultyFilter = facultyDtoMapper.toEntity(filter);
        List<Faculty> faculties = facultyUseCase.getFacultiesByFilter(facultyFilter);
        List<FacultyDto> responseDtos = faculties.stream().map(facultyDtoMapper::toDto).toList();
        return ResponseEntity.ok(responseDtos);
    }
}
