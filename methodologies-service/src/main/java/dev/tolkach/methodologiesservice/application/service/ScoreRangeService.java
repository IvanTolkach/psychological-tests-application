package dev.tolkach.methodologiesservice.application.service;

import dev.tolkach.methodologiesservice.application.model.ScoreRange;
import dev.tolkach.methodologiesservice.application.port.in.ScoreRangeUseCase;
import dev.tolkach.methodologiesservice.application.port.out.ScaleRepository;
import dev.tolkach.methodologiesservice.application.port.out.ScoreRangeRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class ScoreRangeService implements ScoreRangeUseCase {

    private final ScoreRangeRepository scoreRangeRepository;
    private final ScaleRepository scaleRepository;

    public ScoreRangeService(ScoreRangeRepository scoreRangeRepository, ScaleRepository scaleRepository) {
        this.scoreRangeRepository = scoreRangeRepository;
        this.scaleRepository = scaleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScoreRange> getScoreRangesByFilter(ScoreRange filter) {
        if (filter.getScaleId() != null) {
            scaleRepository.findById(filter.getScaleId())
                    .orElseThrow(() -> new NoSuchElementException("Scale not found with id: " + filter.getScaleId()));
        }
        return scoreRangeRepository.findByFilter(filter);
    }

    @Override
    @Transactional
    public ScoreRange createUpdateScoreRange(ScoreRange scoreRange) {
        scaleRepository.findById(scoreRange.getScaleId())
                .orElseThrow(() -> new NoSuchElementException("Scale not found with id: " + scoreRange.getScaleId()));

        if (scoreRange.getMinScore() == null || scoreRange.getMaxScore() == null) {
            throw new IllegalArgumentException("minScore and maxScore are required");
        }
        if (scoreRange.getMinScore() > scoreRange.getMaxScore()) {
            throw new IllegalArgumentException("minScore must be less than or equal to maxScore");
        }
        if (scoreRange.getInterpretation() == null || scoreRange.getInterpretation().isBlank()) {
            throw new IllegalArgumentException("Interpretation is required");
        }

        ScoreRange checkFilter = new ScoreRange();
        checkFilter.setScaleId(scoreRange.getScaleId());
        checkFilter.setInterpretation(scoreRange.getInterpretation());

        List<ScoreRange> existingWithSameInterpretation = scoreRangeRepository.findByFilter(checkFilter);

        boolean interpretationConflict = existingWithSameInterpretation.stream()
                .anyMatch(s -> !s.getId().equals(scoreRange.getId()));

        if (interpretationConflict) {
            throw new IllegalArgumentException(
                    "ScoreRange with interpretation '" + scoreRange.getInterpretation() + "' already exists for scale " + scoreRange.getScaleId()
            );
        }

        checkFilter.setInterpretation(null);

        List<ScoreRange> allRangesInScale = scoreRangeRepository.findByFilter(checkFilter);

        for (ScoreRange existing : allRangesInScale) {
            if (existing.getId() != null && existing.getId().equals(scoreRange.getId())) {
                continue;
            }

            Integer existingMin = existing.getMinScore();
            Integer existingMax = existing.getMaxScore();
            Integer newMin = scoreRange.getMinScore();
            Integer newMax = scoreRange.getMaxScore();

            boolean overlaps = !(newMax < existingMin || newMin > existingMax);

            if (overlaps) {
                throw new IllegalArgumentException(
                        "Score ranges overlap: new range [" + newMin + "–" + newMax + "] " +
                                "conflicts with existing range [" + existingMin + "–" + existingMax + "] " +
                                "in scale " + scoreRange.getScaleId()
                );
            }
        }

        if (scoreRange.getId() == null) {
            return scoreRangeRepository.save(scoreRange);
        } else {
            ScoreRange existing = scoreRangeRepository.findById(scoreRange.getId())
                    .orElseThrow(() -> new NoSuchElementException("ScoreRange not found with id: " + scoreRange.getId()));

            existing.setScaleId(scoreRange.getScaleId());
            existing.setMinScore(scoreRange.getMinScore());
            existing.setMaxScore(scoreRange.getMaxScore());
            existing.setInterpretation(scoreRange.getInterpretation());
            existing.setDescription(scoreRange.getDescription());

            return scoreRangeRepository.save(existing);
        }
    }

    @Override
    @Transactional
    public void deleteScoreRange(UUID id) {
        scoreRangeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("ScoreRange not found with id: " + id));
        scoreRangeRepository.deleteById(id);
    }
}
