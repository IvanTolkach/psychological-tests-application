package common.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class AnswerOptionDto {
    private UUID id;
    private UUID questionId;
    private String text;
    private Integer score;
}
