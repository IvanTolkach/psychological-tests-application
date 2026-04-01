package dev.tolkach.testsservice.application.service;

import dev.tolkach.testsservice.application.model.Test;
import dev.tolkach.testsservice.application.model.TestFilter;
import dev.tolkach.testsservice.application.port.in.TestUseCase;
import dev.tolkach.testsservice.application.port.out.MethodologiesPort;
import dev.tolkach.testsservice.application.port.out.TestRepository;
import dev.tolkach.testsservice.application.port.out.UsersPort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

public class TestService implements TestUseCase {

    private final TestRepository testRepository;
    private final MethodologiesPort methodologiesPort;
    private final UsersPort usersPort;

    public TestService(TestRepository testRepository, MethodologiesPort methodologiesPort, UsersPort usersPort) {
        this.testRepository = testRepository;
        this.methodologiesPort = methodologiesPort;
        this.usersPort = usersPort;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Test> getTestsByFilter(TestFilter filter) {
        if (filter.getMethodologyId() != null) {
            methodologiesPort.validateMethodologyExists(filter.getMethodologyId());
        }
        if (filter.getCreatedBy() != null) {
            usersPort.validateAdminExists(filter.getCreatedBy());
        }
        if (filter.getUpdatedBy() != null) {
            usersPort.validateAdminExists(filter.getUpdatedBy());
        }

        return testRepository.findByFilter(filter);
    }

    @Override
    @Transactional(readOnly = true)
    public Test getTestById(UUID id) {
        return testRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Test not found with id: " + id));
    }

    @Override
    @Transactional
    public Test createUpdateTest(Test test) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object detailsObj = auth.getDetails();

        @SuppressWarnings("unchecked")
        Map<String, Object> details = (Map<String, Object>) detailsObj;
        UUID currentAdminId = (UUID) details.get("adminId");

        methodologiesPort.validateMethodologyExists(test.getMethodologyId());

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
            test.setCreatedBy(currentAdminId);
            test.setCreatedAt(now);
            test.setUpdatedBy(currentAdminId);
            test.setUpdatedAt(now);
            test.setIsActive(false);
            return testRepository.save(test);
        } else {
            Test existing = testRepository.findById(test.getId())
                    .orElseThrow(() -> new NoSuchElementException("Test not found with id: " + test.getId()));

            existing.setName(test.getName());
            existing.setMethodologyId(test.getMethodologyId());
            existing.setUpdatedBy(currentAdminId);
            existing.setUpdatedAt(now);

            return testRepository.save(existing);
        }
    }

    @Override
    @Transactional
    public void updateTestStatus(UUID id) {
        Test existing = testRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Test not found with id: " + id));

        existing.setIsActive(!existing.getIsActive());
        testRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteTest(UUID id) {
        if (testRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException("Test not found with id: " + id);
        }
        testRepository.deleteById(id);
    }
}
