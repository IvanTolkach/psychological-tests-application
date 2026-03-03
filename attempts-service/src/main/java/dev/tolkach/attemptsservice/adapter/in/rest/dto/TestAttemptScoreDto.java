package dev.tolkach.attemptsservice.adapter.in.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class TestAttemptScoreDto {
    private UUID id;

    @NotNull(message = "TestAttempt ID is required")
    private UUID testAttemptId;

    @NotNull(message = "Scale ID is required")
    private UUID scaleId;

    private Integer score;

    private String interpretation;
}
