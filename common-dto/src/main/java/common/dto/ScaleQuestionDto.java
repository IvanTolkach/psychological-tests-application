package common.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ScaleQuestionDto {
    private UUID id;
    private UUID scaleId;
    private UUID questionId;
}