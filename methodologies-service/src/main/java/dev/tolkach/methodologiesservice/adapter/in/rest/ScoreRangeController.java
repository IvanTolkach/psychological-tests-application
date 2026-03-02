package dev.tolkach.methodologiesservice.adapter.in.rest;

import dev.tolkach.methodologiesservice.adapter.in.rest.dto.ScoreRangeDto;
import dev.tolkach.methodologiesservice.adapter.in.rest.endpoint.ScoreRangeEndpoint;
import dev.tolkach.methodologiesservice.adapter.in.rest.mapper.ScoreRangeDtoMapper;
import dev.tolkach.methodologiesservice.application.model.ScoreRange;
import dev.tolkach.methodologiesservice.application.port.in.ScoreRangeUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class ScoreRangeController implements ScoreRangeEndpoint {

    private final ScoreRangeUseCase scoreRangeUseCase;
    private final ScoreRangeDtoMapper scoreRangeDtoMapper;

    public ScoreRangeController(ScoreRangeUseCase scoreRangeUseCase, ScoreRangeDtoMapper scoreRangeDtoMapper) {
        this.scoreRangeUseCase = scoreRangeUseCase;
        this.scoreRangeDtoMapper = scoreRangeDtoMapper;
    }

    @Override
    public ResponseEntity<List<ScoreRangeDto>> getScoreRanges(ScoreRangeDto filter) {
        ScoreRange scoreRangeFilter = scoreRangeDtoMapper.toEntity(filter);
        List<ScoreRange> scoreRanges = scoreRangeUseCase.getScoreRangesByFilter(scoreRangeFilter);
        List<ScoreRangeDto> responseDtos = scoreRanges.stream().map(scoreRangeDtoMapper::toDto).toList();
        return ResponseEntity.ok(responseDtos);
    }

    @Override
    public ResponseEntity<ScoreRangeDto> createUpdateScoreRange(ScoreRangeDto dto) {
        ScoreRange scoreRange = scoreRangeDtoMapper.toEntity(dto);
        ScoreRange saved = scoreRangeUseCase.createUpdateScoreRange(scoreRange);
        ScoreRangeDto responseDto = scoreRangeDtoMapper.toDto(saved);
        if (dto.getId() == null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } else {
            return ResponseEntity.ok(responseDto);
        }
    }

    @Override
    public ResponseEntity<Void> deleteScoreRange(UUID scoreRangeId) {
        scoreRangeUseCase.deleteScoreRange(scoreRangeId);
        return ResponseEntity.noContent().build();
    }
}
