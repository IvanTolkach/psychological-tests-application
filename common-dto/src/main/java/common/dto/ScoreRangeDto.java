package common.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ScoreRangeDto {
    private UUID id;
    private UUID scaleId;
    private Integer minScore;
    private Integer maxScore;
    private String interpretation;
    private String description;
}
