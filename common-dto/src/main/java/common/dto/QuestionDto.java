package common.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class QuestionDto {
    private UUID id;
    private UUID testId;
    private String text;
    private QuestionType type;
    private Integer position;
}
