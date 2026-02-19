package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class TestAttemptDto {
    private UUID id;

    @NotNull(message = "Student ID is required")
    private UUID studentId;

    @NotNull(message = "Test ID is required")
    private UUID testId;

    private LocalDateTime attemptDate;

    private LocalDateTime attemptDateFrom;
    private LocalDateTime attemptDateTo;
}
