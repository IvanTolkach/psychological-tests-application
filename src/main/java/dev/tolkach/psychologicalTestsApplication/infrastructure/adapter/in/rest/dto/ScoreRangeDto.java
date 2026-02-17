package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ScoreRangeDto {
    private UUID id;

    @NotNull(message = "Scale ID is required")
    private UUID scaleId;

    @NotNull(message = "Min score is required")
    private Integer minScore;

    @NotNull(message = "Max score is required")
    private Integer maxScore;

    @NotBlank(message = "Interpretation cannot be blank")
    private String interpretation;

    private String description;
}
