package dev.tolkach.psychologicalTestsApplication.domain.port.in;

import dev.tolkach.psychologicalTestsApplication.domain.model.Methodology;

import java.util.List;
import java.util.UUID;

public interface MethodologyUseCase {
    List<Methodology> getMethodologiesByFilter(Methodology filter);
    Methodology createUpdateMethodology(Methodology methodology);
    void deleteMethodology(UUID id);
}
