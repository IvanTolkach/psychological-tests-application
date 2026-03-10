package dev.tolkach.attemptsservice.application.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ReportRequest {
    private UUID testId;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private List<UUID> facultyIds;
}
