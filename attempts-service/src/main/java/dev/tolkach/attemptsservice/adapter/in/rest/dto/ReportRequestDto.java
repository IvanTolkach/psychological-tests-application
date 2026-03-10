package dev.tolkach.attemptsservice.adapter.in.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ReportRequestDto {
    @NotNull(message = "Test ID is required")
    private UUID testId;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private List<UUID> facultyIds;
}
