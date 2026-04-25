package dev.tolkach.attemptsservice.adapter.out;

import common.dto.ScaleDto;
import common.dto.ScaleQuestionDto;
import common.dto.ScoreRangeDto;
import dev.tolkach.attemptsservice.adapter.out.client.MethodologiesClient;
import dev.tolkach.attemptsservice.adapter.out.client.MethodologiesClientAdapter;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MethodologiesClientAdapterTest {

    @Mock
    MethodologiesClient client;

    MethodologiesClientAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new MethodologiesClientAdapter(client);
    }

    @Test
    void validateScaleExists_success() {
        UUID id = UUID.randomUUID();

        when(client.getScale(id)).thenReturn(new Object());

        assertDoesNotThrow(() -> adapter.validateScaleExists(id));
    }

    @Test
    void validateScaleExists_notFound() {
        UUID id = UUID.randomUUID();

        FeignException ex = mock(FeignException.NotFound.class);
        when(client.getScale(id)).thenThrow(ex);

        assertThrows(NoSuchElementException.class,
                () -> adapter.validateScaleExists(id));
    }

    @Test
    void validateScaleExists_forbidden() {
        UUID id = UUID.randomUUID();

        FeignException ex = mock(FeignException.Forbidden.class);
        when(client.getScale(id)).thenThrow(ex);

        assertThrows(RuntimeException.class,
                () -> adapter.validateScaleExists(id));
    }

    @Test
    void getScalesByMethodologyId() {
        when(client.searchScales(any())).thenReturn(List.of(new ScaleDto()));

        List<ScaleDto> result = adapter.getScalesByMethodologyId(UUID.randomUUID());

        assertEquals(1, result.size());
    }

    @Test
    void getScaleQuestionsByScaleIds() {
        when(client.searchScaleQuestions(any()))
                .thenReturn(List.of(new ScaleQuestionDto()));

        List<ScaleQuestionDto> result =
                adapter.getScaleQuestionsByScaleIds(List.of(UUID.randomUUID()));

        assertEquals(1, result.size());
    }

    @Test
    void getScoreRangesByScaleIds() {
        when(client.searchScoreRanges(any()))
                .thenReturn(List.of(new ScoreRangeDto()));

        List<ScoreRangeDto> result =
                adapter.getScoreRangesByScaleIds(List.of(UUID.randomUUID()));

        assertEquals(1, result.size());
    }
}
