package dev.tolkach.methodologiesservice.adapter.in;

import dev.tolkach.methodologiesservice.adapter.in.rest.ScoreRangeController;
import dev.tolkach.methodologiesservice.adapter.in.rest.dto.ScoreRangeDto;
import dev.tolkach.methodologiesservice.adapter.in.rest.mapper.ScoreRangeDtoMapper;
import dev.tolkach.methodologiesservice.application.model.ScoreRange;
import dev.tolkach.methodologiesservice.application.port.in.ScoreRangeUseCase;
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
class ScoreRangeControllerTest {

    @Mock
    private ScoreRangeUseCase useCase;

    @Mock
    private ScoreRangeDtoMapper mapper;

    @InjectMocks
    private ScoreRangeController controller;

    private ScoreRange entity;
    private ScoreRangeDto dto;
    private UUID id;

    @BeforeEach
    void setup() {
        id = UUID.randomUUID();

        entity = new ScoreRange();
        entity.setId(id);

        dto = new ScoreRangeDto();
        dto.setId(id);
    }

    @Test
    void getScoreRanges_returnsList() {

        when(mapper.toEntity(any())).thenReturn(entity);
        when(useCase.getScoreRangesByFilter(entity))
                .thenReturn(List.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        ResponseEntity<List<ScoreRangeDto>> response =
                controller.getScoreRanges(new ScoreRangeDto());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void createUpdateScoreRange_create_returnsCreated() {

        ScoreRangeDto newDto = new ScoreRangeDto();

        when(mapper.toEntity(newDto)).thenReturn(entity);
        when(useCase.createUpdateScoreRange(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        ResponseEntity<ScoreRangeDto> response =
                controller.createUpdateScoreRange(newDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void createUpdateScoreRange_update_returnsOk() {

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(useCase.createUpdateScoreRange(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        ResponseEntity<ScoreRangeDto> response =
                controller.createUpdateScoreRange(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteScoreRange_returnsNoContent() {

        ResponseEntity<Void> response =
                controller.deleteScoreRange(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(useCase).deleteScoreRange(id);
    }
}
