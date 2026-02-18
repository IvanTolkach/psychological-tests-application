package dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest;

import dev.tolkach.psychologicalTestsApplication.domain.model.AnswerOption;
import dev.tolkach.psychologicalTestsApplication.domain.port.in.AnswerOptionUseCase;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.dto.AnswerOptionDto;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.endpoint.AnswerOptionEndpoint;
import dev.tolkach.psychologicalTestsApplication.infrastructure.adapter.in.rest.mapper.AnswerOptionDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class AnswerOptionController implements AnswerOptionEndpoint {

    private final AnswerOptionUseCase answerOptionUseCase;
    private final AnswerOptionDtoMapper answerOptionDtoMapper;

    public AnswerOptionController(AnswerOptionUseCase answerOptionUseCase, AnswerOptionDtoMapper answerOptionDtoMapper) {
        this.answerOptionUseCase = answerOptionUseCase;
        this.answerOptionDtoMapper = answerOptionDtoMapper;
    }

    @Override
    public ResponseEntity<List<AnswerOptionDto>> getAnswerOptions(AnswerOptionDto filter) {
        AnswerOption answerOptionFilter = answerOptionDtoMapper.toEntity(filter);
        List<AnswerOption> answerOptions = answerOptionUseCase.getAnswerOptionsByFilter(answerOptionFilter);
        List<AnswerOptionDto> responseDtos = answerOptions.stream().map(answerOptionDtoMapper::toDto).toList();
        return ResponseEntity.ok(responseDtos);
    }

    @Override
    public ResponseEntity<AnswerOptionDto> createUpdateAnswerOption(AnswerOptionDto dto) {
        AnswerOption answerOption = answerOptionDtoMapper.toEntity(dto);
        AnswerOption saved = answerOptionUseCase.createUpdateAnswerOption(answerOption);
        AnswerOptionDto responseDto = answerOptionDtoMapper.toDto(saved);
        if (dto.getId() == null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } else {
            return ResponseEntity.ok(responseDto);
        }
    }

    @Override
    public ResponseEntity<Void> deleteAnswerOption(UUID answerOptionId) {
        answerOptionUseCase.deleteAnswerOption(answerOptionId);
        return ResponseEntity.noContent().build();
    }
}