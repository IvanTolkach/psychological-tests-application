package dev.tolkach.psychologicalTestsApplication.domain.port.in;

import dev.tolkach.psychologicalTestsApplication.domain.model.Test;
import dev.tolkach.psychologicalTestsApplication.domain.model.TestFilter;

import java.util.List;
import java.util.UUID;

public interface TestUseCase {
    List<Test> getTestsByFilter(TestFilter filter);
    Test createUpdateTest(Test test);
    void updateTestStatus(UUID id);
    void deleteTest(UUID id);
}
