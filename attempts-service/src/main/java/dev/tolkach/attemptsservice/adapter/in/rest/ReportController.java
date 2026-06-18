package dev.tolkach.attemptsservice.adapter.in.rest;

import dev.tolkach.attemptsservice.adapter.in.rest.dto.ReportRequestDto;
import dev.tolkach.attemptsservice.adapter.in.rest.endpoint.ReportEndpoint;
import dev.tolkach.attemptsservice.adapter.in.rest.mapper.ReportRequestDtoMapper;
import dev.tolkach.attemptsservice.application.port.in.ReportUseCase;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@RestController
public class ReportController implements ReportEndpoint {

    private final ReportUseCase reportUseCase;
    private final ReportRequestDtoMapper reportRequestDtoMapper;

    public ReportController(ReportUseCase reportUseCase, ReportRequestDtoMapper reportRequestDtoMapper) {
        this.reportUseCase = reportUseCase;
        this.reportRequestDtoMapper = reportRequestDtoMapper;
    }

    @Override
    public ResponseEntity<byte[]> generateReport(ReportRequestDto dto) {
        byte[] file = reportUseCase.generateReport(reportRequestDtoMapper.toEntity(dto));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmddMMyy");
        String filename = "report_" + LocalDateTime.now(ZoneId.of("UTC+3")).format(formatter) + ".xlsx";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(file);
    }
}
