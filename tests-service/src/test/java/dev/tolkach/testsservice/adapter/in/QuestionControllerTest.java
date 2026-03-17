package dev.tolkach.testsservice.adapter.in;

import dev.tolkach.testsservice.adapter.in.rest.QuestionController;
import dev.tolkach.testsservice.adapter.in.rest.dto.QuestionDto;
import dev.tolkach.testsservice.adapter.in.rest.mapper.QuestionDtoMapper;
import dev.tolkach.testsservice.application.model.Question;
import dev.tolkach.testsservice.application.port.in.QuestionUseCase;
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
class QuestionControllerTest {

    @Mock
    private QuestionUseCase useCase;

    @Mock
    private QuestionDtoMapper mapper;

    @InjectMocks
    private QuestionController controller;

    private Question question;
    private QuestionDto dto;
    private UUID id;

    @BeforeEach
    void setup() {

        id = UUID.randomUUID();

        question = new Question();
        dto = new QuestionDto();
        dto.setId(id);
    }

    @Test
    void getQuestions_returnsList() {

        when(mapper.toEntity(any())).thenReturn(question);
        when(useCase.getQuestionsByFilter(question)).thenReturn(List.of(question));
        when(mapper.toDto(question)).thenReturn(dto);

        ResponseEntity<List<QuestionDto>> response =
                controller.getQuestions(new QuestionDto());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assert response.getBody() != null;
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getQuestionById_returnsDto() {

        when(useCase.getQuestionById(id)).thenReturn(question);
        when(mapper.toDto(question)).thenReturn(dto);

        ResponseEntity<QuestionDto> response =
                controller.getQuestionById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void createUpdateQuestion_create_returnsCreated() {

        QuestionDto newDto = new QuestionDto();

        when(mapper.toEntity(newDto)).thenReturn(question);
        when(useCase.createUpdateQuestion(question)).thenReturn(question);
        when(mapper.toDto(question)).thenReturn(dto);

        ResponseEntity<QuestionDto> response =
                controller.createUpdateQuestion(newDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void createUpdateQuestion_update_returnsOk() {

        when(mapper.toEntity(dto)).thenReturn(question);
        when(useCase.createUpdateQuestion(question)).thenReturn(question);
        when(mapper.toDto(question)).thenReturn(dto);

        ResponseEntity<QuestionDto> response =
                controller.createUpdateQuestion(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteQuestion_returnsNoContent() {

        ResponseEntity<Void> response =
                controller.deleteQuestion(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(useCase).deleteQuestion(id);
    }
}
