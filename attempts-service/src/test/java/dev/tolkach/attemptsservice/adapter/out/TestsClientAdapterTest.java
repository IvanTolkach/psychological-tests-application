package dev.tolkach.attemptsservice.adapter.out;

import common.dto.AnswerOptionDto;
import common.dto.QuestionDto;
import common.dto.TestDto;
import dev.tolkach.attemptsservice.adapter.out.client.TestsClient;
import dev.tolkach.attemptsservice.adapter.out.client.TestsClientAdapter;
import feign.FeignException;
import feign.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestsClientAdapterTest {

    @Mock
    TestsClient client;

    TestsClientAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new TestsClientAdapter(client);
    }

    @Test
    void validateQuestionExists_success() {
        UUID id = UUID.randomUUID();

        when(client.getQuestion(id)).thenReturn(new Object());

        assertDoesNotThrow(() -> adapter.validateQuestionExists(id));
    }

    @Test
    void validateQuestionExists_fail() {
        UUID id = UUID.randomUUID();

        Request request = Request.create(
                Request.HttpMethod.GET,
                "/questions/" + id,
                Map.of(),
                null,
                Charset.defaultCharset(),
                null
        );

        FeignException ex = new FeignException.NotFound(
                "not found",
                request,
                null,
                null
        );

        when(client.getQuestion(id)).thenThrow(ex);

        assertThrows(NoSuchElementException.class,
                () -> adapter.validateQuestionExists(id));
    }

    @Test
    void validateTestExists_fail() {
        UUID id = UUID.randomUUID();

        when(client.getTest(id)).thenThrow(mock(FeignException.class));

        assertThrows(NoSuchElementException.class,
                () -> adapter.validateTestExists(id));
    }

    @Test
    void validateAnswerOptionExists_fail() {
        UUID id = UUID.randomUUID();

        when(client.getAnswerOption(id)).thenThrow(mock(FeignException.class));

        assertThrows(NoSuchElementException.class,
                () -> adapter.validateAnswerOptionExists(id));
    }

    @Test
    void getTestById() {
        TestDto dto = new TestDto();

        when(client.getTestById(any())).thenReturn(dto);

        assertEquals(dto, adapter.getTestById(UUID.randomUUID()));
    }

    @Test
    void getQuestionsByTestId() {
        when(client.searchQuestions(any())).thenReturn(List.of(new QuestionDto()));

        assertEquals(1,
                adapter.getQuestionsByTestId(UUID.randomUUID()).size());
    }

    @Test
    void getAnswerOptionsByQuestionIds() {
        when(client.searchAnswerOptions(any()))
                .thenReturn(List.of(new AnswerOptionDto()));

        List<AnswerOptionDto> result =
                adapter.getAnswerOptionsByQuestionIds(List.of(UUID.randomUUID()));

        assertEquals(1, result.size());
    }
}
