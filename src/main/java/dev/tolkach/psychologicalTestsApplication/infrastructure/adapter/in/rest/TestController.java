package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest;

import dev.tolkach.psychologicalTestsApplication.domain.model.Test;
import dev.tolkach.psychologicalTestsApplication.domain.model.TestFilter;
import dev.tolkach.psychologicalTestsApplication.domain.port.in.TestUseCase;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto.TestDto;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.endpoint.TestEndpoint;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.mapper.TestDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class TestController implements TestEndpoint {

    private final TestUseCase testUseCase;
    private final TestDtoMapper testDtoMapper;

    public TestController(TestUseCase testUseCase, TestDtoMapper testDtoMapper) {
        this.testUseCase = testUseCase;
        this.testDtoMapper = testDtoMapper;
    }

    @Override
    public ResponseEntity<List<TestDto>> getTests(TestDto filter) {
        TestFilter testFilter = testDtoMapper.toFilter(filter);
        List<Test> tests = testUseCase.getTestsByFilter(testFilter);
        List<TestDto> responseDtos = tests.stream().map(testDtoMapper::toDto).toList();
        return ResponseEntity.ok(responseDtos);
    }

    @Override
    public ResponseEntity<TestDto> createUpdateTest(TestDto dto) {
        Test test = testDtoMapper.toEntity(dto);
        Test saved = testUseCase.createUpdateTest(test);
        TestDto responseDto = testDtoMapper.toDto(saved);
        if (dto.getId() == null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } else {
            return ResponseEntity.ok(responseDto);
        }
    }

    @Override
    public ResponseEntity<Void> updateTestStatus(UUID testId) {
        testUseCase.updateTestStatus(testId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteTest(UUID testId) {
        testUseCase.deleteTest(testId);
        return ResponseEntity.noContent().build();
    }
}