package common.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class StudentAnswerDto {
    private UUID questionId;
    private UUID answerOptionId;
    private String answerValue;
}
