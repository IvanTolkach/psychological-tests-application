package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto;

import dev.tolkach.psychologicalTestsApplication.domain.model.QuestionType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.UUID;

@Data
public class QuestionDto {
    private UUID id;

    @NotNull(message = "Test ID is required")
    private UUID testId;

    @NotBlank(message = "Text cannot be blank")
    private String text;

    @NotNull(message = "Type is required")
    @NotNull(message = "Question type cannot be null")
    private QuestionType type;

    @NotNull(message = "Position is required")
    @PositiveOrZero(message = "Position must be non-negative")
    private Integer position;
}
