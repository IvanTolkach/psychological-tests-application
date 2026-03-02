package dev.tolkach.attemptsservice.adapter.in.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class StudentAnswerDto {
    private UUID id;

    @NotNull(message = "TestAttempt ID is required")
    private UUID testAttemptId;

    @NotNull(message = "Question ID is required")
    private UUID questionId;

    private UUID answerOptionId;

    private String answerValue;
}
