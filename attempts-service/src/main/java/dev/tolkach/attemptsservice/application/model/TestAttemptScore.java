package dev.tolkach.attemptsservice.application.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class TestAttemptScore {
    private UUID id;
    private UUID testAttemptId;
    private UUID scaleId;
    private Integer score;
}
