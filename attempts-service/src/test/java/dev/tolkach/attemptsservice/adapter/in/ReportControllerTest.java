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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmddMMyy");
        String filename = "report_" + LocalDateTime.now(ZoneId.of("UTC+3")).format(formatter) + ".xlsx";

        assertEquals("attachment; filename=" + filename, response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));

        assertEquals(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"), response.getHeaders().getContentType());
    }
}
