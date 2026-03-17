package dev.tolkach.testsservice.adapter.in;

import dev.tolkach.testsservice.adapter.in.rest.AnswerOptionController;
import dev.tolkach.testsservice.adapter.in.rest.dto.AnswerOptionDto;
import dev.tolkach.testsservice.adapter.in.rest.mapper.AnswerOptionDtoMapper;
import dev.tolkach.testsservice.application.model.AnswerOption;
import dev.tolkach.testsservice.application.port.in.AnswerOptionUseCase;
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
class AnswerOptionControllerTest {

    @Mock
    private AnswerOptionUseCase useCase;

    @Mock
    private AnswerOptionDtoMapper mapper;

    @InjectMocks
    private AnswerOptionController controller;

    private AnswerOption answerOption;
    private AnswerOptionDto dto;
    private UUID id;

    @BeforeEach
    void setup() {

        id = UUID.randomUUID();

        answerOption = new AnswerOption();
        dto = new AnswerOptionDto();
        dto.setId(id);
    }

    @Test
    void getAnswerOptions_returnsList() {

        when(mapper.toEntity(any())).thenReturn(answerOption);
        when(useCase.getAnswerOptionsByFilter(answerOption))
                .thenReturn(List.of(answerOption));
        when(mapper.toDto(answerOption)).thenReturn(dto);

        ResponseEntity<List<AnswerOptionDto>> response =
                controller.getAnswerOptions(new AnswerOptionDto());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assert response.getBody() != null;
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getAnswerOptionById_returnsDto() {

        when(useCase.getAnswerOptionById(id)).thenReturn(answerOption);
        when(mapper.toDto(answerOption)).thenReturn(dto);

        ResponseEntity<AnswerOptionDto> response =
                controller.getAnswerOptionById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void createUpdateAnswerOption_create_returnsCreated() {

        AnswerOptionDto newDto = new AnswerOptionDto();

        when(mapper.toEntity(newDto)).thenReturn(answerOption);
        when(useCase.createUpdateAnswerOption(answerOption))
                .thenReturn(answerOption);
        when(mapper.toDto(answerOption)).thenReturn(dto);

        ResponseEntity<AnswerOptionDto> response =
                controller.createUpdateAnswerOption(newDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void createUpdateAnswerOption_update_returnsOk() {

        when(mapper.toEntity(dto)).thenReturn(answerOption);
        when(useCase.createUpdateAnswerOption(answerOption))
                .thenReturn(answerOption);
        when(mapper.toDto(answerOption)).thenReturn(dto);

        ResponseEntity<AnswerOptionDto> response =
                controller.createUpdateAnswerOption(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteAnswerOption_returnsNoContent() {

        ResponseEntity<Void> response =
                controller.deleteAnswerOption(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(useCase).deleteAnswerOption(id);
    }
}