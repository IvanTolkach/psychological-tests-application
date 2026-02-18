package dev.tolkach.psychologicalTestsApplication.domain.port.out;

import dev.tolkach.psychologicalTestsApplication.domain.model.Test;
import dev.tolkach.psychologicalTestsApplication.domain.model.TestFilter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TestRepository {
    Test save(Test test);
    Optional<Test> findById(UUID id);
    List<Test> findByFilter(TestFilter filter);
    void deleteById(UUID id);
}
