package dev.tolkach.attemptsservice.application.port.out;

import common.dto.ScaleDto;
import common.dto.ScaleQuestionDto;
import common.dto.ScoreRangeDto;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface MethodologiesPort {
    void validateScaleExists(UUID scaleId);
    List<ScaleDto> getScalesByMethodologyId(UUID methodologyId);
    List<ScaleQuestionDto> getScaleQuestionsByScaleIds(Collection<UUID> scaleIds);
    List<ScoreRangeDto> getScoreRangesByScaleIds(Collection<UUID> scaleIds);
}
