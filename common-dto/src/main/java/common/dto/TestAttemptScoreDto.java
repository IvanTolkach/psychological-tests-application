package common.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class TestAttemptScoreDto {
    private UUID id;
    private UUID testAttemptId;
    private UUID scaleId;
    private Integer score;
    private String interpretation;
}
