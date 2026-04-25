package dev.tolkach.usersservice.application.port.out;

import dev.tolkach.usersservice.application.model.Faculty;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FacultyRepository {
    List<Faculty> findByFilter(Faculty filter);
    Optional<Faculty> findById(UUID id);
}
