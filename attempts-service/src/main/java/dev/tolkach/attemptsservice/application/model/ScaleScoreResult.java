package dev.tolkach.attemptsservice.application.model;

import java.util.UUID;

public interface ScaleScoreResult {
    UUID getScaleId();
    Integer getScore();
    String getInterpretation();
}
