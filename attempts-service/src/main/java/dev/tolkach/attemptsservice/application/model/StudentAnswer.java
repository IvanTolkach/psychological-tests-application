package dev.tolkach.attemptsservice.application.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class StudentAnswer {
    private UUID id;
    private UUID testAttemptId;
    private UUID questionId;
    private UUID answerOptionId;
    private String answerValue;
}
