package dev.tolkach.methodologiesservice.adapter.out;

import dev.tolkach.methodologiesservice.adapter.out.client.TestsClient;
import dev.tolkach.methodologiesservice.adapter.out.client.TestsClientAdapter;
import feign.FeignException;
import feign.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestsClientAdapterTest {

    @Mock
    TestsClient testsClient;

    TestsClientAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new TestsClientAdapter(testsClient);
    }

    @Test
    void validateQuestionExists_success() {
        UUID questionId = UUID.randomUUID();
        when(testsClient.getQuestion(questionId)).thenReturn(new Object());

        assertDoesNotThrow(() -> adapter.validateQuestionExists(questionId));
    }

    @Test
    void validateQuestionExists_notFound_throws() {
        UUID questionId = UUID.randomUUID();
        Request request = Request.create(Request.HttpMethod.GET, "/questions/" + questionId, Map.of(), null, Charset.defaultCharset(), null);
        FeignException.NotFound exFeign = new FeignException.NotFound("not found", request, null, null);

        when(testsClient.getQuestion(questionId)).thenThrow(exFeign);

        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> adapter.validateQuestionExists(questionId));
        assertTrue(ex.getMessage().contains(questionId.toString()));
    }

    @Test
    void validateQuestionExists_forbidden_throws() {
        UUID questionId = UUID.randomUUID();
        Request request = Request.create(Request.HttpMethod.GET, "/questions/" + questionId, Map.of(), null, Charset.defaultCharset(), null);

        FeignException.Forbidden exFeign = new FeignException.Forbidden(
                "forbidden for id: " + questionId, request, null, null);

        when(testsClient.getQuestion(questionId)).thenThrow(exFeign);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> adapter.validateQuestionExists(questionId));

        assertTrue(ex.getMessage().contains(questionId.toString()));
    }
}
