package dev.tolkach.methodologiesservice.adapter.in;

import dev.tolkach.methodologiesservice.adapter.in.rest.ScaleController;
import dev.tolkach.methodologiesservice.adapter.in.rest.dto.ScaleDto;
import dev.tolkach.methodologiesservice.adapter.in.rest.mapper.ScaleDtoMapper;
import dev.tolkach.methodologiesservice.application.model.Scale;
import dev.tolkach.methodologiesservice.application.port.in.ScaleUseCase;
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
class ScaleControllerTest {

    @Mock
    private ScaleUseCase useCase;

    @Mock
    private ScaleDtoMapper mapper;

    @InjectMocks
    private ScaleController controller;

    private Scale scale;
    private ScaleDto dto;
    private UUID id;

    @BeforeEach
    void setup() {
        id = UUID.randomUUID();

        scale = new Scale();
        scale.setId(id);

        dto = new ScaleDto();
        dto.setId(id);
    }

    @Test
    void getScales_returnsList() {

        when(mapper.toEntity(any())).thenReturn(scale);
        when(useCase.getScalesByFilter(scale)).thenReturn(List.of(scale));
        when(mapper.toDto(scale)).thenReturn(dto);

        ResponseEntity<List<ScaleDto>> response =
                controller.getScales(new ScaleDto());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getScaleById_returnsDto() {

        when(useCase.getScaleById(id)).thenReturn(scale);
        when(mapper.toDto(scale)).thenReturn(dto);

        ResponseEntity<ScaleDto> response =
                controller.getScaleById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void createUpdateScale_create_returnsCreated() {

        ScaleDto newDto = new ScaleDto();

        when(mapper.toEntity(newDto)).thenReturn(scale);
        when(useCase.createUpdateScale(scale)).thenReturn(scale);
        when(mapper.toDto(scale)).thenReturn(dto);

        ResponseEntity<ScaleDto> response =
                controller.createUpdateScale(newDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void createUpdateScale_update_returnsOk() {

        when(mapper.toEntity(dto)).thenReturn(scale);
        when(useCase.createUpdateScale(scale)).thenReturn(scale);
        when(mapper.toDto(scale)).thenReturn(dto);

        ResponseEntity<ScaleDto> response =
                controller.createUpdateScale(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteScale_returnsNoContent() {

        ResponseEntity<Void> response =
                controller.deleteScale(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(useCase).deleteScale(id);
    }
}