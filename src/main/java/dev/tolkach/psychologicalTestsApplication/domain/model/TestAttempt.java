package dev.tolkach.psychologicalTestsApplication.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class TestAttempt {
    private UUID id;
    private UUID studentId;
    private UUID testId;
    private LocalDateTime attemptDate;
}
