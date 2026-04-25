package dev.tolkach.testsservice.application.port.out;

import dev.tolkach.testsservice.application.model.Test;
import dev.tolkach.testsservice.application.model.TestFilter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TestRepository {
    Test save(Test test);
    Optional<Test> findById(UUID id);
    List<Test> findByFilter(TestFilter filter);
    void deleteById(UUID id);
}
