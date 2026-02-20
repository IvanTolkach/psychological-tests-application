package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto;

import dev.tolkach.psychologicalTestsApplication.domain.model.QuestionType;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class QuestionDto {
    private UUID id;

    @NotNull(message = "Test ID is required")
    private UUID testId;

    @NotBlank(message = "Text cannot be blank")
    private String text;

    @NotNull(message = "Type is required")
    @NotNull(message = "Question type cannot be null")
    private QuestionType type;

    @PositiveOrZero(message = "Position must be non-negative")
    private Integer position;
}
