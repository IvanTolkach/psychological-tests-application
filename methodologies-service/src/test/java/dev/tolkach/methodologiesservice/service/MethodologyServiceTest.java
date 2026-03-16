package dev.tolkach.methodologiesservice.service;

import dev.tolkach.methodologiesservice.application.model.Methodology;
import dev.tolkach.methodologiesservice.application.port.out.MethodologyRepository;
import dev.tolkach.methodologiesservice.application.service.MethodologyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MethodologyServiceTest {

    @Mock
    MethodologyRepository methodologyRepository;

    @InjectMocks
    MethodologyService service;

    Methodology methodology;
    UUID id;

    @BeforeEach
    void setup() {
        id = UUID.randomUUID();
        methodology = new Methodology();
        methodology.setId(id);
        methodology.setName("Example Methodology");
        methodology.setDescription("Description example");
    }

    @Test
    void getMethodologiesByFilter_callsRepository() {
        when(methodologyRepository.findByFilter(methodology)).thenReturn(List.of(methodology));
        List<Methodology> result = service.getMethodologiesByFilter(methodology);
        assertEquals(1, result.size());
        assertEquals(methodology, result.getFirst());
    }

    @Test
    void getMethodologyById_found() {
        when(methodologyRepository.findById(id)).thenReturn(Optional.of(methodology));
        Methodology result = service.getMethodologyById(id);
        assertEquals(methodology, result);
    }

    @Test
    void getMethodologyById_notFound_throws() {
        when(methodologyRepository.findById(id)).thenReturn(Optional.empty());
        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> service.getMethodologyById(id));
        assertTrue(ex.getMessage().contains("Methodology not found"));
    }

    @Test
    void createUpdateMethodology_createNew() {
        methodology.setId(null);

        when(methodologyRepository.findByFilter(any())).thenReturn(List.of());
        when(methodologyRepository.save(methodology)).thenReturn(methodology);

        Methodology result = service.createUpdateMethodology(methodology);
        assertEquals(methodology, result);
    }

    @Test
    void createUpdateMethodology_updateExisting() {
        methodology.setId(UUID.randomUUID());
        when(methodologyRepository.findByFilter(any())).thenReturn(List.of());
        when(methodologyRepository.findById(methodology.getId())).thenReturn(Optional.of(methodology));
        when(methodologyRepository.save(any())).thenReturn(methodology);

        Methodology updated = new Methodology();
        updated.setId(methodology.getId());
        updated.setName("Updated Name");
        updated.setDescription("Updated Desc");

        Methodology result = service.createUpdateMethodology(updated);
        assertEquals("Updated Name", result.getName());
        assertEquals("Updated Desc", result.getDescription());
    }

    @Test
    void createUpdateMethodology_duplicateName_throws() {
        Methodology other = new Methodology();
        other.setId(UUID.randomUUID());
        other.setName(methodology.getName());

        when(methodologyRepository.findByFilter(any())).thenReturn(List.of(other));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.createUpdateMethodology(methodology));
        assertTrue(ex.getMessage().contains("already exists"));
    }

    @Test
    void createUpdateMethodology_updateNotFound_throws() {
        when(methodologyRepository.findByFilter(any())).thenReturn(List.of());
        when(methodologyRepository.findById(id)).thenReturn(Optional.empty());

        methodology.setId(id);
        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> service.createUpdateMethodology(methodology));
        assertTrue(ex.getMessage().contains("Methodology not found"));
    }

    @Test
    void deleteMethodology_found() {
        when(methodologyRepository.findById(id)).thenReturn(Optional.of(methodology));
        service.deleteMethodology(id);
        verify(methodologyRepository).deleteById(id);
    }

    @Test
    void deleteMethodology_notFound_throws() {
        when(methodologyRepository.findById(id)).thenReturn(Optional.empty());
        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> service.deleteMethodology(id));
        assertTrue(ex.getMessage().contains("Methodology not found"));
    }
}
