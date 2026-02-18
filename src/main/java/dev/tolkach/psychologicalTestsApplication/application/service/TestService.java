package dev.tolkach.psychologicalTestsApplication.application.service;

import dev.tolkach.psychologicalTestsApplication.domain.model.Test;
import dev.tolkach.psychologicalTestsApplication.domain.model.TestFilter;
import dev.tolkach.psychologicalTestsApplication.domain.port.in.TestUseCase;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.AdminRepository;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.MethodologyRepository;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.TestRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class TestService implements TestUseCase {

    private final TestRepository testRepository;
    private final MethodologyRepository methodologyRepository;
    private final AdminRepository adminRepository;

    public TestService(TestRepository testRepository, MethodologyRepository methodologyRepository, AdminRepository adminRepository) {
        this.testRepository = testRepository;
        this.methodologyRepository = methodologyRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public List<Test> getTestsByFilter(TestFilter filter) {
        if (filter.getMethodologyId() != null) {
            methodologyRepository.findById(filter.getMethodologyId())
                    .orElseThrow(() -> new IllegalArgumentException("Methodology not found with id: " + filter.getMethodologyId()));
        }
        if (filter.getCreatedBy() != null) {
            adminRepository.findById(filter.getCreatedBy())
                    .orElseThrow(() -> new IllegalArgumentException("Creator admin not found with id: " + filter.getCreatedBy()));
        }
        if (filter.getUpdatedBy() != null) {
            adminRepository.findById(filter.getUpdatedBy())
                    .orElseThrow(() -> new IllegalArgumentException("Updater admin not found with id: " + filter.getUpdatedBy()));
        }

        return testRepository.findByFilter(filter);
    }

    @Override
    public Test createUpdateTest(Test test) {
        methodologyRepository.findById(test.getMethodologyId())
                .orElseThrow(() -> new IllegalArgumentException("Methodology not found with id: " + test.getMethodologyId()));

        adminRepository.findById(test.getCreatedBy())
                .orElseThrow(() -> new IllegalArgumentException("Creator admin not found with id: " + test.getCreatedBy()));

        if (test.getUpdatedBy() != null) {
            adminRepository.findById(test.getUpdatedBy())
                    .orElseThrow(() -> new IllegalArgumentException("Updater admin not found with id: " + test.getUpdatedBy()));
        }

        TestFilter checkFilter = new TestFilter();
        checkFilter.setMethodologyId(test.getMethodologyId());
        checkFilter.setName(test.getName());

        List<Test> existingWithSameName = testRepository.findByFilter(checkFilter);

        boolean nameConflict = existingWithSameName.stream()
                .anyMatch(t -> !t.getId().equals(test.getId()));

        if (nameConflict) {
            throw new IllegalArgumentException(
                    "Test with name '" + test.getName() + "' already exists for methodology " + test.getMethodologyId()
            );
        }

        LocalDateTime now = LocalDateTime.now();
        if (test.getId() == null) {
            test.setCreatedAt(now);
            test.setUpdatedAt(now);
            test.setIsActive(false);
            return testRepository.save(test);
        } else {
            Test existing = testRepository.findById(test.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Test not found with id: " + test.getId()));

            existing.setName(test.getName());
            existing.setMethodologyId(test.getMethodologyId());
            existing.setUpdatedAt(now);

            return testRepository.save(existing);
        }
    }

    @Override
    public void updateTestStatus(UUID id) {
        Test existing = testRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Test not found with id: " + id));

        existing.setIsActive(!existing.getIsActive());
        testRepository.save(existing);
    }

    @Override
    public void deleteTest(UUID id) {
        if (testRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Test not found with id: " + id);
        }
        testRepository.deleteById(id);
    }
}
