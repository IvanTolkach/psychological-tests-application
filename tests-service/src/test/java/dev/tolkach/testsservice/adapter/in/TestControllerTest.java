package dev.tolkach.testsservice.adapter.in;

import dev.tolkach.testsservice.adapter.in.rest.TestController;
import dev.tolkach.testsservice.adapter.in.rest.dto.TestDto;
import dev.tolkach.testsservice.adapter.in.rest.mapper.TestDtoMapper;
import dev.tolkach.testsservice.application.model.TestFilter;
import dev.tolkach.testsservice.application.port.in.TestUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestControllerTest {

    @Mock
    private TestUseCase useCase;

    @Mock
    private TestDtoMapper mapper;

    @InjectMocks
    private TestController controller;

    private dev.tolkach.testsservice.application.model.Test test;
    private TestDto dto;
    private UUID id;

    @BeforeEach
    void setup() {

        id = UUID.randomUUID();

        test = new dev.tolkach.testsservice.application.model.Test();
        dto = new TestDto();
        dto.setId(id);
    }

    @Test
    void getTests_returnsList() {

        TestFilter filter = new TestFilter();

        when(mapper.toFilter(any())).thenReturn(filter);
        when(useCase.getTestsByFilter(filter)).thenReturn(List.of(test));
        when(mapper.toDto(test)).thenReturn(dto);

        ResponseEntity<List<TestDto>> response =
                controller.getTests(new TestDto());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assert response.getBody() != null;
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getTestById_returnsDto() {

        when(useCase.getTestById(id)).thenReturn(test);
        when(mapper.toDto(test)).thenReturn(dto);

        ResponseEntity<TestDto> response =
                controller.getTestById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void createUpdateTest_create_returnsCreated() {

        TestDto newDto = new TestDto();

        when(mapper.toEntity(newDto)).thenReturn(test);
        when(useCase.createUpdateTest(test)).thenReturn(test);
        when(mapper.toDto(test)).thenReturn(dto);

        ResponseEntity<TestDto> response =
                controller.createUpdateTest(newDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void createUpdateTest_update_returnsOk() {

        when(mapper.toEntity(dto)).thenReturn(test);
        when(useCase.createUpdateTest(test)).thenReturn(test);
        when(mapper.toDto(test)).thenReturn(dto);

        ResponseEntity<TestDto> response =
                controller.createUpdateTest(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updateTestStatus_returnsNoContent() {

        ResponseEntity<Void> response =
                controller.updateTestStatus(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(useCase).updateTestStatus(id);
    }

    @Test
    void deleteTest_returnsNoContent() {

        ResponseEntity<Void> response =
                controller.deleteTest(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(useCase).deleteTest(id);
    }
}