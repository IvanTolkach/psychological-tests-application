package dev.tolkach.attemptsservice.adapter.in;

import dev.tolkach.attemptsservice.adapter.in.rest.TestAttemptScoreController;
import dev.tolkach.attemptsservice.adapter.in.rest.dto.TestAttemptScoreDto;
import dev.tolkach.attemptsservice.adapter.in.rest.mapper.TestAttemptScoreDtoMapper;
import dev.tolkach.attemptsservice.application.model.TestAttemptScore;
import dev.tolkach.attemptsservice.application.port.in.TestAttemptScoreUseCase;
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
class TestAttemptScoreControllerTest {

    @Mock
    TestAttemptScoreUseCase useCase;

    @Mock
    TestAttemptScoreDtoMapper mapper;

    @InjectMocks
    TestAttemptScoreController controller;

    TestAttemptScore entity;
    TestAttemptScoreDto dto;
    UUID id;

    @BeforeEach
    void setup() {
        id = UUID.randomUUID();
        entity = new TestAttemptScore();
        dto = new TestAttemptScoreDto();
        dto.setId(id);
    }

    @Test
    void getTestAttemptScores_returnsList() {

        when(mapper.toEntity(any())).thenReturn(entity);
        when(useCase.getTestAttemptScoresByFilter(entity)).thenReturn(List.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        ResponseEntity<List<TestAttemptScoreDto>> response = controller.getTestAttemptScores(new TestAttemptScoreDto());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void createUpdateTestAttemptScore_create_returnsCreated() {

        TestAttemptScoreDto newDto = new TestAttemptScoreDto();

        when(mapper.toEntity(newDto)).thenReturn(entity);
        when(useCase.createUpdateTestAttemptScore(entity)).thenReturn(List.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        ResponseEntity<List<TestAttemptScoreDto>> response = controller.createUpdateTestAttemptScore(newDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void createUpdateTestAttemptScore_update_returnsOk() {

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(useCase.createUpdateTestAttemptScore(entity)).thenReturn(List.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        ResponseEntity<List<TestAttemptScoreDto>> response = controller.createUpdateTestAttemptScore(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteTestAttemptScore_returnsNoContent() {

        ResponseEntity<Void> response = controller.deleteTestAttemptScore(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(useCase).deleteTestAttemptScore(id);
    }
}
