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
        String filename = LocalDateTime.now() + "_report";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=report.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);
    }
}
