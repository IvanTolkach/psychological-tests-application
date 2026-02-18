package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest;

import dev.tolkach.psychologicalTestsApplication.domain.model.ScaleQuestion;
import dev.tolkach.psychologicalTestsApplication.domain.port.in.ScaleQuestionUseCase;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto.ScaleQuestionDto;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.endpoint.ScaleQuestionEndpoint;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.mapper.ScaleQuestionDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class ScaleQuestionController implements ScaleQuestionEndpoint {

    private final ScaleQuestionUseCase scaleQuestionUseCase;
    private final ScaleQuestionDtoMapper scaleQuestionDtoMapper;

    public ScaleQuestionController(ScaleQuestionUseCase scaleQuestionUseCase, ScaleQuestionDtoMapper scaleQuestionDtoMapper) {
        this.scaleQuestionUseCase = scaleQuestionUseCase;
        this.scaleQuestionDtoMapper = scaleQuestionDtoMapper;
    }

    @Override
    public ResponseEntity<List<ScaleQuestionDto>> getScaleQuestions(ScaleQuestionDto filter) {
        ScaleQuestion scaleQuestionFilter = scaleQuestionDtoMapper.toEntity(filter);
        List<ScaleQuestion> scaleQuestions = scaleQuestionUseCase.getScaleQuestionsByFilter(scaleQuestionFilter);
        List<ScaleQuestionDto> responseDtos = scaleQuestions.stream().map(scaleQuestionDtoMapper::toDto).toList();
        return ResponseEntity.ok(responseDtos);
    }

    @Override
    public ResponseEntity<ScaleQuestionDto> createUpdateScaleQuestion(ScaleQuestionDto dto) {
        ScaleQuestion scaleQuestion = scaleQuestionDtoMapper.toEntity(dto);
        ScaleQuestion saved = scaleQuestionUseCase.createScaleQuestion(scaleQuestion);
        ScaleQuestionDto responseDto = scaleQuestionDtoMapper.toDto(saved);
        if (dto.getId() == null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } else {
            return ResponseEntity.ok(responseDto);
        }
    }

    @Override
    public ResponseEntity<Void> deleteScaleQuestion(UUID scaleQuestionId) {
        scaleQuestionUseCase.deleteScaleQuestion(scaleQuestionId);
        return ResponseEntity.noContent().build();
    }
}