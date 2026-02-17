package dev.tolkach.psychologicalTestsApplication.domain.port.out;

import dev.tolkach.psychologicalTestsApplication.domain.model.Methodology;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MethodologyRepository {
    Methodology save(Methodology methodology);
    Optional<Methodology> findById(UUID id);
    List<Methodology> findByFilter(Methodology filter);
    void deleteById(UUID id);
}
