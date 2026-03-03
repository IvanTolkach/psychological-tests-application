package common.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class TestAttemptCreateDto {
    private UUID studentId;
    private UUID testId;
    private List<StudentAnswerDto> answers;
}