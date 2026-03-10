package dev.tolkach.attemptsservice.adapter.in.rest.endpoint;

import dev.tolkach.attemptsservice.adapter.in.rest.dto.ReportRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ReportEndpoint {
    @PostMapping(ApiEndpoints.Report.BASE)
    @PreAuthorize("hasAnyAuthority('ROLE_STANDARD', 'ROLE_SUPER')")
    ResponseEntity<byte[]> generateReport(@Valid @RequestBody ReportRequestDto dto);
}
