package dev.tolkach.attemptsservice.adapter.in;

import dev.tolkach.attemptsservice.adapter.in.rest.StudentAnswerController;
import dev.tolkach.attemptsservice.adapter.in.rest.dto.StudentAnswerDto;
import dev.tolkach.attemptsservice.adapter.in.rest.mapper.StudentAnswerDtoMapper;
import dev.tolkach.attemptsservice.application.model.StudentAnswer;
import dev.tolkach.attemptsservice.application.port.in.StudentAnswerUseCase;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentAnswerControllerTest {

    @Mock
    StudentAnswerUseCase useCase;

    @Mock
    StudentAnswerDtoMapper mapper;

    @InjectMocks
    StudentAnswerController controller;

    StudentAnswer entity;
    StudentAnswerDto dto;
    UUID id;

    @BeforeEach
    void setup() {
        id = UUID.randomUUID();
        entity = new StudentAnswer();
        dto = new StudentAnswerDto();
        dto.setId(id);
    }

    @Test
    void getStudentAnswers_returnsList() {

        when(mapper.toEntity(any())).thenReturn(entity);
        when(useCase.getStudentAnswersByFilter(entity)).thenReturn(List.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        ResponseEntity<List<StudentAnswerDto>> response = controller.getStudentAnswers(new StudentAnswerDto());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void createUpdateStudentAnswer_create_returnsCreated() {

        StudentAnswerDto newDto = new StudentAnswerDto(); // id = null

        when(mapper.toEntity(newDto)).thenReturn(entity);
        when(useCase.createUpdateStudentAnswer(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        ResponseEntity<StudentAnswerDto> response = controller.createUpdateStudentAnswer(newDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void createUpdateStudentAnswer_update_returnsOk() {

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(useCase.createUpdateStudentAnswer(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        ResponseEntity<StudentAnswerDto> response = controller.createUpdateStudentAnswer(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteStudentAnswer_returnsNoContent() {

        ResponseEntity<Void> response = controller.deleteStudentAnswer(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(useCase).deleteStudentAnswer(id);
    }
}
