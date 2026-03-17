package dev.tolkach.testsservice.adapter.out;

import dev.tolkach.testsservice.adapter.out.client.MethodologiesClient;
import dev.tolkach.testsservice.adapter.out.client.MethodologiesClientAdapter;
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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

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
    void validateMethodologyExists_success() {

        UUID id = UUID.randomUUID();

        when(client.getMethodology(id)).thenReturn(new Object());

        assertDoesNotThrow(() -> adapter.validateMethodologyExists(id));
    }

    @Test
    void validateMethodologyExists_notFound() {

        UUID id = UUID.randomUUID();

        Request request = Request.create(
                Request.HttpMethod.GET,
                "/m/" + id,
                Map.of(),
                null,
                Charset.defaultCharset(),
                null
        );

        FeignException.NotFound ex =
                new FeignException.NotFound("not found", request, null, null);

        when(client.getMethodology(id)).thenThrow(ex);

        assertThrows(
                NoSuchElementException.class,
                () -> adapter.validateMethodologyExists(id)
        );
    }

    @Test
    void validateMethodologyExists_forbidden() {

        UUID id = UUID.randomUUID();

        Request request = Request.create(
                Request.HttpMethod.GET,
                "/m/" + id,
                Map.of(),
                null,
                Charset.defaultCharset(),
                null
        );

        FeignException.Forbidden ex =
                new FeignException.Forbidden("forbidden", request, null, null);

        when(client.getMethodology(id)).thenThrow(ex);

        assertThrows(
                NoSuchElementException.class,
                () -> adapter.validateMethodologyExists(id)
        );
    }
}
