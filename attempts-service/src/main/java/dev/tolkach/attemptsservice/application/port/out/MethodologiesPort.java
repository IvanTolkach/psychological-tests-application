package dev.tolkach.attemptsservice.application.port.out;

import java.util.UUID;

public interface MethodologiesPort {
    void validateScaleExists(UUID scaleId);
}
