package dev.tolkach.attemptsservice.adapter.in.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime attemptDateFrom;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime attemptDateTo;
}
