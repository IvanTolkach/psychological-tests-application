package dev.tolkach.methodologiesservice.application.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ScoreRange {
    private UUID id;
    private UUID scaleId;
    private Integer minScore;
    private Integer maxScore;
    private String interpretation;
    private String description;
}
