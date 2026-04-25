package dev.tolkach.methodologiesservice.service;

import dev.tolkach.methodologiesservice.application.model.Scale;
import dev.tolkach.methodologiesservice.application.model.ScoreRange;
import dev.tolkach.methodologiesservice.application.port.out.ScaleRepository;
import dev.tolkach.methodologiesservice.application.port.out.ScoreRangeRepository;
import dev.tolkach.methodologiesservice.application.service.ScoreRangeService;
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
class ScoreRangeServiceTest {

    @Mock
    ScoreRangeRepository scoreRangeRepository;

    @Mock
    ScaleRepository scaleRepository;

    @InjectMocks
    ScoreRangeService service;

    UUID scaleId = UUID.randomUUID();
    UUID scoreRangeId = UUID.randomUUID();
    ScoreRange scoreRange;

    @BeforeEach
    void setup() {
        scoreRange = new ScoreRange();
        scoreRange.setId(scoreRangeId);
        scoreRange.setScaleId(scaleId);
        scoreRange.setMinScore(10);
        scoreRange.setMaxScore(20);
        scoreRange.setInterpretation("Good");
        scoreRange.setDescription("Description");
    }

    @Test
    void getScoreRangesByFilter_scaleExists() {
        when(scaleRepository.findById(scaleId)).thenReturn(Optional.of(new Scale()));
        when(scoreRangeRepository.findByFilter(scoreRange)).thenReturn(List.of(scoreRange));

        List<ScoreRange> result = service.getScoreRangesByFilter(scoreRange);

        assertEquals(1, result.size());
        assertEquals(scoreRange, result.getFirst());
    }

    @Test
    void getScoreRangesByFilter_scaleNotFound_throws() {
        when(scaleRepository.findById(scaleId)).thenReturn(Optional.empty());

        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> service.getScoreRangesByFilter(scoreRange));

        assertTrue(ex.getMessage().contains("Scale not found"));
    }

    @Test
    void createUpdateScoreRange_createNew_success() {
        scoreRange.setId(null);
        when(scaleRepository.findById(scaleId)).thenReturn(Optional.of(new Scale()));
        when(scoreRangeRepository.findByFilter(any())).thenReturn(List.of());
        when(scoreRangeRepository.save(scoreRange)).thenReturn(scoreRange);

        ScoreRange result = service.createUpdateScoreRange(scoreRange);
        assertEquals(scoreRange, result);
    }

    @Test
    void createUpdateScoreRange_updateExisting_success() {
        when(scaleRepository.findById(scaleId)).thenReturn(Optional.of(new Scale()));
        when(scoreRangeRepository.findByFilter(any())).thenReturn(List.of());
        when(scoreRangeRepository.findById(scoreRangeId)).thenReturn(Optional.of(scoreRange));
        when(scoreRangeRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ScoreRange updated = new ScoreRange();
        updated.setId(scoreRangeId);
        updated.setScaleId(scaleId);
        updated.setMinScore(15);
        updated.setMaxScore(25);
        updated.setInterpretation("Very Good");
        updated.setDescription("Updated");

        ScoreRange result = service.createUpdateScoreRange(updated);

        assertEquals("Very Good", result.getInterpretation());
        assertEquals(15, result.getMinScore());
        assertEquals(25, result.getMaxScore());
    }

    @Test
    void createUpdateScoreRange_scaleNotFound_throws() {
        when(scaleRepository.findById(scaleId)).thenReturn(Optional.empty());

        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> service.createUpdateScoreRange(scoreRange));

        assertTrue(ex.getMessage().contains("Scale not found"));
    }

    @Test
    void createUpdateScoreRange_missingMinMax_throws() {
        scoreRange.setMinScore(null);

        when(scaleRepository.findById(scaleId)).thenReturn(Optional.of(new Scale()));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.createUpdateScoreRange(scoreRange));

        assertTrue(ex.getMessage().contains("minScore and maxScore"));
    }

    @Test
    void createUpdateScoreRange_minGreaterThanMax_throws() {
        scoreRange.setMinScore(30);
        scoreRange.setMaxScore(20);

        when(scaleRepository.findById(scaleId)).thenReturn(Optional.of(new Scale()));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.createUpdateScoreRange(scoreRange));

        assertTrue(ex.getMessage().contains("minScore must be less"));
    }

    @Test
    void createUpdateScoreRange_missingInterpretation_throws() {
        scoreRange.setInterpretation("   ");

        when(scaleRepository.findById(scaleId)).thenReturn(Optional.of(new Scale()));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.createUpdateScoreRange(scoreRange));

        assertTrue(ex.getMessage().contains("Interpretation is required"));
    }

    @Test
    void createUpdateScoreRange_interpretationConflict_throws() {
        ScoreRange other = new ScoreRange();
        other.setId(UUID.randomUUID());
        other.setInterpretation("Good");

        when(scaleRepository.findById(scaleId)).thenReturn(Optional.of(new Scale()));
        when(scoreRangeRepository.findByFilter(any())).thenReturn(List.of(other));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.createUpdateScoreRange(scoreRange));

        assertTrue(ex.getMessage().contains("already exists"));
    }

    @Test
    void createUpdateScoreRange_overlap_throws() {
        UUID scaleId = UUID.randomUUID();
        ScoreRange scoreRange = new ScoreRange();
        scoreRange.setScaleId(scaleId);
        scoreRange.setMinScore(20);
        scoreRange.setMaxScore(30);
        scoreRange.setInterpretation("Good");

        ScoreRange overlapping = new ScoreRange();
        overlapping.setId(UUID.randomUUID());
        overlapping.setMinScore(15);
        overlapping.setMaxScore(25);

        when(scaleRepository.findById(scaleId)).thenReturn(Optional.of(new Scale()));

        when(scoreRangeRepository.findByFilter(any())).thenAnswer(invocation -> {
            ScoreRange filter = invocation.getArgument(0);
            if (filter == null) {
                return List.of();
            }
            if (filter.getInterpretation() == null) {
                return List.of(overlapping);
            } else {
                return List.of();
            }
        });

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.createUpdateScoreRange(scoreRange));

        assertTrue(ex.getMessage().contains("overlap"));
    }

    @Test
    void deleteScoreRange_success() {
        when(scoreRangeRepository.findById(scoreRangeId)).thenReturn(Optional.of(scoreRange));

        service.deleteScoreRange(scoreRangeId);

        verify(scoreRangeRepository).deleteById(scoreRangeId);
    }

    @Test
    void deleteScoreRange_notFound_throws() {
        when(scoreRangeRepository.findById(scoreRangeId)).thenReturn(Optional.empty());

        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> service.deleteScoreRange(scoreRangeId));

        assertTrue(ex.getMessage().contains("ScoreRange not found"));
    }
}
