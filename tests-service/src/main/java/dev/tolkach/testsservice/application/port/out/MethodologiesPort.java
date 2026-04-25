package dev.tolkach.testsservice.application.port.out;

import java.util.UUID;

public interface MethodologiesPort {
    void validateMethodologyExists(UUID methodologyId);
}

