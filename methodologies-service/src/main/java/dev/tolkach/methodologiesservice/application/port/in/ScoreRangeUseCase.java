package dev.tolkach.methodologiesservice.application.port.in;

import dev.tolkach.methodologiesservice.application.model.ScoreRange;

import java.util.List;
import java.util.UUID;

public interface ScoreRangeUseCase {
    List<ScoreRange> getScoreRangesByFilter(ScoreRange filter);
    ScoreRange createUpdateScoreRange(ScoreRange scoreRange);
    void deleteScoreRange(UUID id);
}
