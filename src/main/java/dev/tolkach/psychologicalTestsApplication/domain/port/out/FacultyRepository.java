package dev.tolkach.psychologicalTestsApplication.domain.port.out;

import dev.tolkach.psychologicalTestsApplication.domain.model.Faculty;

import java.util.Optional;
import java.util.UUID;

public interface FacultyRepository {
    Optional<Faculty> findById(UUID id);
}
