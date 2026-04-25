package dev.tolkach.methodologiesservice.service;

import dev.tolkach.methodologiesservice.application.model.Methodology;
import dev.tolkach.methodologiesservice.application.model.Scale;
import dev.tolkach.methodologiesservice.application.port.out.MethodologyRepository;
import dev.tolkach.methodologiesservice.application.port.out.ScaleRepository;
import dev.tolkach.methodologiesservice.application.service.ScaleService;
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
class ScaleServiceTest {

    @Mock
    ScaleRepository scaleRepository;

    @Mock
    MethodologyRepository methodologyRepository;

    @InjectMocks
    ScaleService service;

    UUID scaleId, methodologyId;
    Scale scale;

    @BeforeEach
    void setup() {
        scaleId = UUID.randomUUID();
        methodologyId = UUID.randomUUID();
        scale = new Scale();
        scale.setId(scaleId);
        scale.setMethodologyId(methodologyId);
        scale.setName("Example Scale");
        scale.setIsTotal(false);
    }

    @Test
    void getScalesByFilter_methodologyExists() {
        when(methodologyRepository.findById(methodologyId)).thenReturn(Optional.of(new Methodology()));
        when(scaleRepository.findByFilter(scale)).thenReturn(List.of(scale));

        List<Scale> result = service.getScalesByFilter(scale);
        assertEquals(1, result.size());
    }

    @Test
    void getScalesByFilter_methodologyNotFound_throws() {
        when(methodologyRepository.findById(methodologyId)).thenReturn(Optional.empty());
        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> service.getScalesByFilter(scale));
        assertTrue(ex.getMessage().contains("Methodology not found"));
    }

    @Test
    void getScaleById_found() {
        when(scaleRepository.findById(scaleId)).thenReturn(Optional.of(scale));
        assertEquals(scale, service.getScaleById(scaleId));
    }

    @Test
    void getScaleById_notFound() {
        when(scaleRepository.findById(scaleId)).thenReturn(Optional.empty());
        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> service.getScaleById(scaleId));
        assertTrue(ex.getMessage().contains("Scale not found"));
    }

    @Test
    void createUpdateScale_createNew() {
        scale.setId(null);
        when(methodologyRepository.findById(methodologyId)).thenReturn(Optional.of(new Methodology()));
        when(scaleRepository.findByFilter(any())).thenReturn(List.of());
        when(scaleRepository.save(scale)).thenReturn(scale);

        Scale result = service.createUpdateScale(scale);

        assertEquals(scale, result);
    }

    @Test
    void createUpdateScale_updateExisting() {
        UUID scaleId = UUID.randomUUID();
        scale.setId(scaleId);

        Scale existing = new Scale();
        existing.setId(scaleId);
        existing.setName("Old Name");
        existing.setMethodologyId(methodologyId);

        when(methodologyRepository.findById(methodologyId)).thenReturn(Optional.of(new Methodology()));
        when(scaleRepository.findById(scaleId)).thenReturn(Optional.of(existing));
        when(scaleRepository.findByFilter(any())).thenReturn(List.of());
        when(scaleRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Scale updated = new Scale();
        updated.setId(scaleId);
        updated.setName("New Name");
        updated.setMethodologyId(methodologyId);

        Scale result = service.createUpdateScale(updated);

        assertEquals("New Name", result.getName());
        assertEquals(methodologyId, result.getMethodologyId());
    }

    @Test
    void createUpdateScale_nameConflict_throwsException() {
        UUID existingId = UUID.randomUUID();
        Scale conflicting = new Scale();
        conflicting.setId(existingId);
        conflicting.setName("Scale A");
        conflicting.setMethodologyId(methodologyId);

        when(methodologyRepository.findById(methodologyId)).thenReturn(Optional.of(new Methodology()));
        when(scaleRepository.findByFilter(any())).thenReturn(List.of(conflicting));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.createUpdateScale(scale));

        assertTrue(ex.getMessage().contains("already exists"));
    }

    @Test
    void createUpdateScale_totalScaleConflict_throwsException() {
        scale.setIsTotal(true);
        Scale existingTotal = new Scale();
        existingTotal.setId(UUID.randomUUID());
        existingTotal.setIsTotal(true);

        when(methodologyRepository.findById(methodologyId)).thenReturn(Optional.of(new Methodology()));
        when(scaleRepository.findByFilter(any())).thenAnswer(invocation -> {
            Scale arg = invocation.getArgument(0);
            if (Boolean.TRUE.equals(arg.getIsTotal())) {
                return List.of(existingTotal);
            }
            return List.of();
        });

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.createUpdateScale(scale));

        assertTrue(ex.getMessage().contains("already has a total scale"));
    }

    @Test
    void createUpdateScale_methodologyNotFound_throwsException() {
        when(methodologyRepository.findById(methodologyId)).thenReturn(Optional.empty());

        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> service.createUpdateScale(scale));

        assertTrue(ex.getMessage().contains("Methodology not found"));
    }

    @Test
    void createUpdateScale_updateExisting_scaleNotFound_throwsException() {
        UUID scaleId = UUID.randomUUID();
        scale.setId(scaleId);

        when(methodologyRepository.findById(methodologyId)).thenReturn(Optional.of(new Methodology()));
        when(scaleRepository.findById(scaleId)).thenReturn(Optional.empty());
        when(scaleRepository.findByFilter(any())).thenReturn(List.of());

        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> service.createUpdateScale(scale));

        assertTrue(ex.getMessage().contains("Scale not found"));
    }

    @Test
    void deleteScale_found() {
        when(scaleRepository.findById(scaleId)).thenReturn(Optional.of(scale));
        service.deleteScale(scaleId);
        verify(scaleRepository).deleteById(scaleId);
    }

    @Test
    void deleteScale_notFound_throws() {
        when(scaleRepository.findById(scaleId)).thenReturn(Optional.empty());
        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> service.deleteScale(scaleId));
        assertTrue(ex.getMessage().contains("Scale not found"));
    }
}