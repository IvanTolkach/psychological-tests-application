package dev.tolkach.attemptsservice.adapter.in;

import dev.tolkach.attemptsservice.adapter.in.rest.ReportController;
import dev.tolkach.attemptsservice.adapter.in.rest.dto.ReportRequestDto;
import dev.tolkach.attemptsservice.adapter.in.rest.mapper.ReportRequestDtoMapper;
import dev.tolkach.attemptsservice.application.model.ReportRequest;
import dev.tolkach.attemptsservice.application.port.in.ReportUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportControllerTest {

    @Mock
    ReportUseCase useCase;

    @Mock
    ReportRequestDtoMapper mapper;

    @InjectMocks
    ReportController controller;

    @Test
    void generateReport_returnsFile() {

        ReportRequestDto dto = new ReportRequestDto();
        ReportRequest entity = new ReportRequest();
        byte[] file = new byte[]{1, 2, 3};

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(useCase.generateReport(entity)).thenReturn(file);

        ResponseEntity<byte[]> response = controller.generateReport(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(file, response.getBody());

        assertEquals("attachment; filename=report.xlsx", response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));

        assertEquals(MediaType.APPLICATION_OCTET_STREAM, response.getHeaders().getContentType());
    }
}
