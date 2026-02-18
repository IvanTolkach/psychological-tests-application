package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class AnswerOptionDto {
    private UUID id;

    @NotNull(message = "Question ID is required")
    private UUID questionId;

    @NotBlank(message = "Text cannot be blank")
    private String text;

    @NotNull(message = "Score is required")
    private Integer score;
}
