package dev.tolkach.methodologiesservice.adapter.in;

import dev.tolkach.methodologiesservice.adapter.in.rest.ScaleQuestionController;
import dev.tolkach.methodologiesservice.adapter.in.rest.dto.ScaleQuestionDto;
import dev.tolkach.methodologiesservice.adapter.in.rest.mapper.ScaleQuestionDtoMapper;
import dev.tolkach.methodologiesservice.application.model.ScaleQuestion;
import dev.tolkach.methodologiesservice.application.port.in.ScaleQuestionUseCase;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScaleQuestionControllerTest {

    @Mock
    private ScaleQuestionUseCase useCase;

    @Mock
    private ScaleQuestionDtoMapper mapper;

    @InjectMocks
    private ScaleQuestionController controller;

    private ScaleQuestion entity;
    private ScaleQuestionDto dto;
    private UUID id;

    @BeforeEach
    void setup() {
        id = UUID.randomUUID();

        entity = new ScaleQuestion();
        entity.setId(id);

        dto = new ScaleQuestionDto();
        dto.setId(id);
    }

    @Test
    void getScaleQuestions_returnsList() {

        when(mapper.toEntity(any())).thenReturn(entity);
        when(useCase.getScaleQuestionsByFilter(entity))
                .thenReturn(List.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        ResponseEntity<List<ScaleQuestionDto>> response =
                controller.getScaleQuestions(new ScaleQuestionDto());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void createUpdateScaleQuestion_create_returnsCreated() {

        ScaleQuestionDto newDto = new ScaleQuestionDto();

        when(mapper.toEntity(newDto)).thenReturn(entity);
        when(useCase.createScaleQuestion(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        ResponseEntity<ScaleQuestionDto> response =
                controller.createUpdateScaleQuestion(newDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void createUpdateScaleQuestion_update_returnsOk() {

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(useCase.createScaleQuestion(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        ResponseEntity<ScaleQuestionDto> response =
                controller.createUpdateScaleQuestion(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteScaleQuestion_returnsNoContent() {

        ResponseEntity<Void> response =
                controller.deleteScaleQuestion(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(useCase).deleteScaleQuestion(id);
    }
}