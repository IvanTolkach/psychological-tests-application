package dev.tolkach.usersservice.adapter.in;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import dev.tolkach.usersservice.adapter.in.rest.FacultyController;
import dev.tolkach.usersservice.adapter.in.rest.dto.FacultyDto;
import dev.tolkach.usersservice.adapter.in.rest.mapper.FacultyDtoMapper;
import dev.tolkach.usersservice.application.model.Faculty;
import dev.tolkach.usersservice.application.port.in.FacultyUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class FacultyControllerTest {

    @Mock
    FacultyUseCase facultyUseCase;

    @Mock
    FacultyDtoMapper facultyDtoMapper;

    @InjectMocks
    FacultyController controller;

    private Faculty faculty;
    private FacultyDto facultyDto;

    @BeforeEach
    void setup() {
        faculty = new Faculty();
        faculty.setId(UUID.randomUUID());
        faculty.setName("Science");

        facultyDto = new FacultyDto();
        facultyDto.setId(faculty.getId());
        facultyDto.setName(faculty.getName());
    }

    @Test
    void getFaculties_returnsDtoList() {
        when(facultyDtoMapper.toEntity(facultyDto)).thenReturn(faculty);
        when(facultyUseCase.getFacultiesByFilter(faculty)).thenReturn(List.of(faculty));
        when(facultyDtoMapper.toDto(faculty)).thenReturn(facultyDto);

        ResponseEntity<List<FacultyDto>> response = controller.getFaculties(facultyDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(facultyDto, response.getBody().getFirst());
    }
}
