package dev.tolkach.methodologiesservice.adapter.in;

import dev.tolkach.methodologiesservice.adapter.in.rest.MethodologyController;
import dev.tolkach.methodologiesservice.adapter.in.rest.dto.MethodologyDto;
import dev.tolkach.methodologiesservice.adapter.in.rest.mapper.MethodologyDtoMapper;
import dev.tolkach.methodologiesservice.application.model.Methodology;
import dev.tolkach.methodologiesservice.application.port.in.MethodologyUseCase;
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
class MethodologyControllerTest {

    @Mock
    private MethodologyUseCase useCase;

    @Mock
    private MethodologyDtoMapper mapper;

    @InjectMocks
    private MethodologyController controller;

    private Methodology methodology;
    private MethodologyDto dto;
    private UUID id;

    @BeforeEach
    void setup() {
        id = UUID.randomUUID();

        methodology = new Methodology();
        methodology.setId(id);

        dto = new MethodologyDto();
        dto.setId(id);
    }

    @Test
    void getMethodologies_returnsList() {

        when(mapper.toEntity(any())).thenReturn(methodology);
        when(useCase.getMethodologiesByFilter(methodology))
                .thenReturn(List.of(methodology));
        when(mapper.toDto(methodology)).thenReturn(dto);

        ResponseEntity<List<MethodologyDto>> response =
                controller.getMethodologies(new MethodologyDto());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getMethodologyById_returnsDto() {

        when(useCase.getMethodologyById(id)).thenReturn(methodology);
        when(mapper.toDto(methodology)).thenReturn(dto);

        ResponseEntity<MethodologyDto> response =
                controller.getMethodologyById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void createUpdateMethodology_create_returnsCreated() {

        MethodologyDto newDto = new MethodologyDto();

        when(mapper.toEntity(newDto)).thenReturn(methodology);
        when(useCase.createUpdateMethodology(methodology))
                .thenReturn(methodology);
        when(mapper.toDto(methodology)).thenReturn(dto);

        ResponseEntity<MethodologyDto> response =
                controller.createUpdateMethodology(newDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void createUpdateMethodology_update_returnsOk() {

        when(mapper.toEntity(dto)).thenReturn(methodology);
        when(useCase.createUpdateMethodology(methodology))
                .thenReturn(methodology);
        when(mapper.toDto(methodology)).thenReturn(dto);

        ResponseEntity<MethodologyDto> response =
                controller.createUpdateMethodology(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteMethodology_returnsNoContent() {

        ResponseEntity<Void> response =
                controller.deleteMethodology(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(useCase).deleteMethodology(id);
    }
}