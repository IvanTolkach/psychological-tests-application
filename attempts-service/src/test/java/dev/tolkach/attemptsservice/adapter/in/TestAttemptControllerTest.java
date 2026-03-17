package dev.tolkach.attemptsservice.adapter.in;

import dev.tolkach.attemptsservice.adapter.in.rest.TestAttemptController;
import dev.tolkach.attemptsservice.adapter.in.rest.dto.TestAttemptDto;
import dev.tolkach.attemptsservice.adapter.in.rest.mapper.TestAttemptDtoMapper;
import dev.tolkach.attemptsservice.application.model.TestAttempt;
import dev.tolkach.attemptsservice.application.model.TestAttemptFilter;
import dev.tolkach.attemptsservice.application.port.in.TestAttemptUseCase;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestAttemptControllerTest {

    @Mock
    TestAttemptUseCase useCase;

    @Mock
    TestAttemptDtoMapper mapper;

    @InjectMocks
    TestAttemptController controller;

    TestAttempt entity;
    TestAttemptDto dto;
    UUID id;

    @BeforeEach
    void setup() {
        id = UUID.randomUUID();
        entity = new TestAttempt();
        dto = new TestAttemptDto();
        dto.setId(id);
    }

    @Test
    void getTestAttempts_returnsList() {

        when(mapper.toFilter(any())).thenReturn(new TestAttemptFilter());
        when(useCase.getTestAttemptsByFilter(any())).thenReturn(List.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        ResponseEntity<List<TestAttemptDto>> response = controller.getTestAttempts(new TestAttemptDto());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void createTestAttempt_create_returnsCreated() {

        TestAttemptDto newDto = new TestAttemptDto();

        when(mapper.toEntity(newDto)).thenReturn(entity);
        when(useCase.createUpdateTestAttempt(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        ResponseEntity<TestAttemptDto> response = controller.createTestAttempt(newDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void createTestAttempt_update_returnsOk() {

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(useCase.createUpdateTestAttempt(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        ResponseEntity<TestAttemptDto> response = controller.createTestAttempt(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteTestAttempt_returnsNoContent() {

        ResponseEntity<Void> response = controller.deleteTestAttempt(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(useCase).deleteTestAttempt(id);
    }
}
