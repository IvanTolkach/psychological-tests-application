package dev.tolkach.psychologicalTestsApplication.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class AnswerOption {
    private UUID id;
    private UUID questionId;
    private String text;
    private Integer score;
}
