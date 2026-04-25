package dev.tolkach.methodologiesservice.application.port.in;


import dev.tolkach.methodologiesservice.application.model.Methodology;

import java.util.List;
import java.util.UUID;

public interface MethodologyUseCase {
    List<Methodology> getMethodologiesByFilter(Methodology filter);
    Methodology getMethodologyById(UUID id);
    Methodology createUpdateMethodology(Methodology methodology);
    void deleteMethodology(UUID id);
}
