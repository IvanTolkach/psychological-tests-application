package dev.tolkach.testsservice.adapter.out;

import dev.tolkach.testsservice.adapter.out.client.UsersClient;
import dev.tolkach.testsservice.adapter.out.client.UsersClientAdapter;
import feign.FeignException;
import feign.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsersClientAdapterTest {

    @Mock
    UsersClient client;

    UsersClientAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new UsersClientAdapter(client);
    }

    @Test
    void validateAdminExists_success() {

        UUID id = UUID.randomUUID();

        when(client.getAdmin(id)).thenReturn(new Object());

        assertDoesNotThrow(() -> adapter.validateAdminExists(id));
    }

    @Test
    void validateAdminExists_notFound() {

        UUID id = UUID.randomUUID();

        Request request = Request.create(
                Request.HttpMethod.GET,
                "/admin/" + id,
                Map.of(),
                null,
                Charset.defaultCharset(),
                null
        );

        FeignException.NotFound ex =
                new FeignException.NotFound("not found", request, null, null);

        when(client.getAdmin(id)).thenThrow(ex);

        assertThrows(
                NoSuchElementException.class,
                () -> adapter.validateAdminExists(id)
        );
    }

    @Test
    void validateAdminExists_forbidden() {

        UUID id = UUID.randomUUID();

        Request request = Request.create(
                Request.HttpMethod.GET,
                "/admin/" + id,
                Map.of(),
                null,
                Charset.defaultCharset(),
                null
        );

        FeignException.Forbidden ex =
                new FeignException.Forbidden("forbidden", request, null, null);

        when(client.getAdmin(id)).thenThrow(ex);

        assertThrows(
                RuntimeException.class,
                () -> adapter.validateAdminExists(id)
        );
    }

    @Test
    void validateAdminExists_unauthorized_throwsAccessDenied() {
        UUID id = UUID.randomUUID();
        Request request = Request.create(
                Request.HttpMethod.GET,
                "/admin/" + id,
                Map.of(),
                null,
                Charset.defaultCharset(),
                null
        );

        FeignException.Unauthorized ex =
                new FeignException.Unauthorized("unauthorized", request, null, null);

        when(client.getAdmin(id)).thenThrow(ex);

        AccessDeniedException thrown = assertThrows(
                AccessDeniedException.class,
                () -> adapter.validateAdminExists(id)
        );

        assertEquals("You have no permission to execute this operation with Admin's parameters", thrown.getMessage());
    }
}
