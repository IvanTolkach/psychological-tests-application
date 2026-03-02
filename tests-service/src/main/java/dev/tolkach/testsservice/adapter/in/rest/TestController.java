package dev.tolkach.testsservice.adapter.in.rest;

import dev.tolkach.testsservice.adapter.in.rest.dto.TestDto;
import dev.tolkach.testsservice.adapter.in.rest.endpoint.TestEndpoint;
import dev.tolkach.testsservice.adapter.in.rest.mapper.TestDtoMapper;
import dev.tolkach.testsservice.application.model.Test;
import dev.tolkach.testsservice.application.model.TestFilter;
import dev.tolkach.testsservice.application.port.in.TestUseCase;
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
    public ResponseEntity<TestDto> getTestById(UUID testId) {
        Test test = testUseCase.getTestById(testId);
        TestDto responseDto = testDtoMapper.toDto(test);
        return ResponseEntity.ok(responseDto);
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