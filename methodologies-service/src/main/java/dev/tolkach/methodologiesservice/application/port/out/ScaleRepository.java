package dev.tolkach.methodologiesservice.application.port.out;

import dev.tolkach.methodologiesservice.application.model.Scale;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ScaleRepository {
    Scale save(Scale scale);
    Optional<Scale> findById(UUID id);
    List<Scale> findByFilter(Scale filter);
    void deleteById(UUID id);
}
