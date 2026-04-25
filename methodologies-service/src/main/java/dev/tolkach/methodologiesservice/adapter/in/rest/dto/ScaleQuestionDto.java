package dev.tolkach.methodologiesservice.adapter.in.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ScaleQuestionDto {
    private UUID id;

    @NotNull(message = "Scale ID is required")
    private UUID scaleId;

    @NotNull(message = "Question ID is required")
    private UUID questionId;
}
