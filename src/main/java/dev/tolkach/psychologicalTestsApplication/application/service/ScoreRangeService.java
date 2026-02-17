package dev.tolkach.psychologicalTestsApplication.application.service;

import dev.tolkach.psychologicalTestsApplication.domain.model.ScoreRange;
import dev.tolkach.psychologicalTestsApplication.domain.port.in.ScoreRangeUseCase;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.ScaleRepository;
import dev.tolkach.psychologicalTestsApplication.domain.port.out.ScoreRangeRepository;

import java.util.List;
import java.util.UUID;

public class ScoreRangeService implements ScoreRangeUseCase {

    private final ScoreRangeRepository scoreRangeRepository;
    private final ScaleRepository scaleRepository;

    public ScoreRangeService(ScoreRangeRepository scoreRangeRepository, ScaleRepository scaleRepository) {
        this.scoreRangeRepository = scoreRangeRepository;
        this.scaleRepository = scaleRepository;
    }

    @Override
    public List<ScoreRange> getScoreRangesByFilter(ScoreRange filter) {
        if (filter.getScaleId() != null) {
            scaleRepository.findById(filter.getScaleId())
                    .orElseThrow(() -> new IllegalArgumentException("Scale not found with id: " + filter.getScaleId()));
        }
        return scoreRangeRepository.findByFilter(filter);
    }

    @Override
    public ScoreRange createUpdateScoreRange(ScoreRange scoreRange) {
        scaleRepository.findById(scoreRange.getScaleId())
                .orElseThrow(() -> new IllegalArgumentException("Scale not found with id: " + scoreRange.getScaleId()));

        if (scoreRange.getMinScore() == null || scoreRange.getMaxScore() == null) {
            throw new IllegalArgumentException("minScore and maxScore are required");
        }
        if (scoreRange.getMinScore() > scoreRange.getMaxScore()) {
            throw new IllegalArgumentException("minScore must be less than or equal to maxScore");
        }
        if (scoreRange.getInterpretation() == null || scoreRange.getInterpretation().isBlank()) {
            throw new IllegalArgumentException("Interpretation is required");
        }

        if (scoreRange.getId() == null) {
            return scoreRangeRepository.save(scoreRange);
        } else {
            ScoreRange existing = scoreRangeRepository.findById(scoreRange.getId())
                    .orElseThrow(() -> new IllegalArgumentException("ScoreRange not found with id: " + scoreRange.getId()));

            existing.setScaleId(scoreRange.getScaleId());
            existing.setMinScore(scoreRange.getMinScore());
            existing.setMaxScore(scoreRange.getMaxScore());
            existing.setInterpretation(scoreRange.getInterpretation());
            existing.setDescription(scoreRange.getDescription());

            return scoreRangeRepository.save(existing);
        }
    }

    @Override
    public void deleteScoreRange(UUID id) {
        scoreRangeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ScoreRange not found with id: " + id));
        scoreRangeRepository.deleteById(id);
    }
}
