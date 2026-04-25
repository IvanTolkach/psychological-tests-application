package dev.tolkach.testsservice.adapter.in.rest;

import dev.tolkach.testsservice.adapter.in.rest.dto.QuestionDto;
import dev.tolkach.testsservice.adapter.in.rest.endpoint.QuestionEndpoint;
import dev.tolkach.testsservice.adapter.in.rest.mapper.QuestionDtoMapper;
import dev.tolkach.testsservice.application.model.Question;
import dev.tolkach.testsservice.application.port.in.QuestionUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class QuestionController implements QuestionEndpoint {

    private final QuestionUseCase questionUseCase;
    private final QuestionDtoMapper questionDtoMapper;

    public QuestionController(QuestionUseCase questionUseCase, QuestionDtoMapper questionDtoMapper) {
        this.questionUseCase = questionUseCase;
        this.questionDtoMapper = questionDtoMapper;
    }

    @Override
    public ResponseEntity<List<QuestionDto>> getQuestions(QuestionDto filter) {
        Question questionFilter = questionDtoMapper.toEntity(filter);
        List<Question> questions = questionUseCase.getQuestionsByFilter(questionFilter);
        List<QuestionDto> responseDtos = questions.stream().map(questionDtoMapper::toDto).toList();
        return ResponseEntity.ok(responseDtos);
    }

    @Override
    public ResponseEntity<QuestionDto> getQuestionById(UUID questionId) {
        Question question = questionUseCase.getQuestionById(questionId);
        QuestionDto responseDto = questionDtoMapper.toDto(question);
        return ResponseEntity.ok(responseDto);
    }

    @Override
    public ResponseEntity<QuestionDto> createUpdateQuestion(QuestionDto dto) {
        Question question = questionDtoMapper.toEntity(dto);
        Question saved = questionUseCase.createUpdateQuestion(question);
        QuestionDto responseDto = questionDtoMapper.toDto(saved);
        if (dto.getId() == null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } else {
            return ResponseEntity.ok(responseDto);
        }
    }

    @Override
    public ResponseEntity<Void> deleteQuestion(UUID questionId) {
        questionUseCase.deleteQuestion(questionId);
        return ResponseEntity.noContent().build();
    }
}
