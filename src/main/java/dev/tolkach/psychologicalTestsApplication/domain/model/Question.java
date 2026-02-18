package dev.tolkach.psychologicalTestsApplication.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class Question {
    private UUID id;
    private UUID testId;
    private String text;
    private QuestionType type;
    private Integer position;
}
