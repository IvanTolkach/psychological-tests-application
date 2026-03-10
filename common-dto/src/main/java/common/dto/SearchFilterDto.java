package common.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class SearchFilterDto {
    private UUID methodologyId;
    private UUID testId;
    private UUID scaleId;
    private UUID questionId;
    private UUID scaleQuestionId;
    private UUID facultyId;
    private UUID studentId;
}
