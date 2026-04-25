package dev.tolkach.testsservice.application.port.in;

import dev.tolkach.testsservice.application.model.Test;
import dev.tolkach.testsservice.application.model.TestFilter;

import java.util.List;
import java.util.UUID;

public interface TestUseCase {
    List<Test> getTestsByFilter(TestFilter filter);
    Test getTestById(UUID id);
    Test createUpdateTest(Test test);
    void updateTestStatus(UUID id);
    void deleteTest(UUID id);
}
