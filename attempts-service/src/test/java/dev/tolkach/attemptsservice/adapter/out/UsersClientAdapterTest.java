package dev.tolkach.attemptsservice.adapter.out;

import common.dto.FacultyDto;
import common.dto.StudentDto;
import dev.tolkach.attemptsservice.adapter.out.client.UsersClient;
import dev.tolkach.attemptsservice.adapter.out.client.UsersClientAdapter;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
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
    void validateStudentExists_success() {
        UUID id = UUID.randomUUID();

        when(client.getStudent(id)).thenReturn(new StudentDto());

        assertDoesNotThrow(() -> adapter.validateStudentExists(id));
    }

    @Test
    void getStudentById_success() {
        UUID id = UUID.randomUUID();
        StudentDto student = new StudentDto();
        student.setId(id);

        when(client.getStudent(id)).thenReturn(student);

        assertEquals(student, adapter.getStudentById(id));
    }

    @Test
    void getStudentById_fail() {
        UUID id = UUID.randomUUID();

        when(client.getStudent(id)).thenThrow(mock(FeignException.NotFound.class));

        assertThrows(NoSuchElementException.class,
                () -> adapter.getStudentById(id));
    }

    @Test
    void validateStudentExists_fail() {
        UUID id = UUID.randomUUID();

        when(client.getStudent(id)).thenThrow(mock(FeignException.NotFound.class));

        assertThrows(NoSuchElementException.class,
                () -> adapter.validateStudentExists(id));
    }

    @Test
    void validateStudentExists_unexpectedException_throwsRuntimeException() {
        UUID id = UUID.randomUUID();
        String errorMessage = "Unexpected connection error";

        when(client.getStudent(id)).thenThrow(new RuntimeException(errorMessage));

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> adapter.validateStudentExists(id)
        );

        assertEquals(errorMessage, ex.getMessage());
    }

    @Test
    void searchStudents() {
        when(client.searchStudents(any()))
                .thenReturn(List.of(new StudentDto()));

        assertEquals(1,
                adapter.searchStudents(new StudentDto()).size());
    }

    @Test
    void getAllFaculties() {
        when(client.searchFaculties(any()))
                .thenReturn(List.of(new FacultyDto()));

        assertEquals(1, adapter.getAllFaculties().size());
    }
}
